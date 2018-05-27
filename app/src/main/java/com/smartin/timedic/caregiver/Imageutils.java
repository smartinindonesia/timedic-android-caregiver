package com.smartin.timedic.caregiver;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.smartin.timedic.caregiver.config.Constants;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.parammodel.UserProfile;
import com.smartin.timedic.caregiver.tools.restservice.UserAPIInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

@SuppressLint("SdCardPath")
public class Imageutils
{

    Context context;
    private Activity current_activity;
    private Fragment current_fragment;

    private ImageAttachmentListener imageAttachment_callBack;


    private String selected_path="";
    private Uri imageUri;
    private File path = null;

    private int from=0;
    private String type = "";
    private boolean isFragment=false;

    private UserAPIInterface userAPIInterface;
    private HomecareSessionManager homecareSessionManager;
    private SweetAlertDialog progressDialog;

    public Imageutils(Activity act, HomecareSessionManager homecare, UserAPIInterface userAPI) {

        this.context=act;
        this.current_activity = act;
        imageAttachment_callBack = (ImageAttachmentListener)context;
        userAPIInterface = userAPI;
        homecareSessionManager = homecare;
        //System.out.println("Ini dia : "+homecareSessionManager.getUserDetail().getId());
    }

    public Imageutils(Activity act, Fragment fragment, boolean isFragment) {

        this.context=act;
        this.current_activity = act;
        imageAttachment_callBack= (ImageAttachmentListener) fragment;
        if(isFragment)
        {
            this.isFragment=true;
            current_fragment=fragment;
        }

    }



    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = this.context.getContentResolver().query(uri, projection, null, null, null);
        int column_index = 0;
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path=cursor.getString(column_index);
            cursor.close();
            return path;
        }
        else
            return uri.getPath();
    }


    public boolean isDeviceSupportCamera() {
        if (this.context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }



    public Bitmap compressImage(String imageUri, float height, float width) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        // by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
        // you try the use the bitmap here, you will get null.

        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        // max Height and width values of the compressed image is taken as 816x612

        float maxHeight = height;
        float maxWidth = width;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        // width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        //  setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        //  inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

        // this options allow android to claim the bitmap memory if it runs low on memory

        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            //  load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        // check the rotation of the image and display it properly

        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);

            return scaledBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get RealPath from Content URI
     *
     * @param contentURI
     * @return
     */
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }


    /**
     * ImageSize Calculation
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    /**
     * Launch Camera
     *
     * @param from
     */

    public void launchCamera(int from)
    {
        this.from=from;

        if (Build.VERSION.SDK_INT >= 23)
        {
            if(isFragment)
                permission_check_fragment(1);
            else
                permission_check(1);
        }
        else
        {
            camera_call();
        }
    }

    /**
     * Launch Gallery
     *
     * @param from
     */

    public void launchGallery(int from)
    {

        this.from=from;

        if (Build.VERSION.SDK_INT >= 23)
        {
            if(isFragment)
                permission_check_fragment(2);
            else
                permission_check(2);
        }
        else
        {
            galley_call();
        }
    }

    /**
     * Show AlertDialog with the following options
     *
     *          Camera
     *          Gallery
     *
     *
     * @param from
     */

    public void imagepicker(final int from, final String type)
    {
        this.from = from;
        this.type = type;

        final CharSequence[] items;

        if(isDeviceSupportCamera())
        {
            items=new CharSequence[2];
            items[0]="Camera";
            items[1]="Gallery";
        }

        else
        {
            items=new CharSequence[1];
            items[0]="Gallery";
        }

        android.app.AlertDialog.Builder alertdialog = new android.app.AlertDialog.Builder(current_activity);
        alertdialog.setTitle("Pilih Foto");
        alertdialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera"))
                {
                    launchCamera(from);
                }
                else if (items[item].equals("Gallery"))
                {
                    launchGallery(from);
                }
            }
        });
        alertdialog.show();
    }

    /**
     * Check permission
     *
     * @param code
     */

    public void permission_check(final int code)
    {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(current_activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)
        {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(current_activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                showMessageOKCancel("For adding images , You need to provide permission to access your files",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ActivityCompat.requestPermissions(current_activity,
                                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        code);
                            }
                        });
                return;
            }

            ActivityCompat.requestPermissions(current_activity,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    code);
            return;
        }

        if(code==1)
            camera_call();
        else if(code==2)
            galley_call();
    }


    /**
     * Check permission
     *
     * @param code
     */

    public void permission_check_fragment(final int code)
    {
        Log.d(TAG, "permission_check_fragment: "+code);
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(current_activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)

        {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(current_activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                showMessageOKCancel("For adding images , You need to provide permission to access your files",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                current_fragment.requestPermissions(
                                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        code);
                            }
                        });
                return;
            }

            current_fragment.requestPermissions(
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    code);
            return;
        }

        if(code==1)
            camera_call();
        else if(code==2)
            galley_call();
    }




    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(current_activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    /**
     * Capture image from camera
     */

    public void camera_call()
    {
        ContentValues values = new ContentValues();
        imageUri = current_activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        if(isFragment)
            current_fragment.startActivityForResult(intent1,0);
        else
            current_activity.startActivityForResult(intent1, 0);
    }

    /**
     * pick image from Gallery
     *
     */

    public void galley_call()
    {
        Log.d(TAG, "galley_call: ");

        Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent2.setType("image/*");

        if(isFragment)
            current_fragment.startActivityForResult(intent2,1);
        else
            current_activity.startActivityForResult(intent2, 1);
    }


    public void request_permission_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    camera_call();
                } else {
                    Toast.makeText(current_activity, "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
            case 2:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galley_call();
                } else {
                    Toast.makeText(current_activity, "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        String file_name;
        Bitmap bitmap;
        switch (requestCode){
            case 0:
                if(resultCode==current_activity.RESULT_OK){
                    Log.i("Camera Selected","Photo");
                    try{
                        selected_path = null;
                        selected_path = getPath(imageUri);
                        AsyncTask<String, Integer, String> x = new Upload().execute(null,null,null);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                if(resultCode==current_activity.RESULT_OK)
                {
                    Log.i("Gallery","Photo");
                    Uri selectedImage = data.getData();
                    try {
                        selected_path = null;
                        selected_path = getPath(selectedImage);
                        //file_name = selected_path.substring(selected_path.lastIndexOf("/")+1);
                        //bitmap = compressImage(selectedImage.toString(),716,512);
                        AsyncTask<String, Integer, String> x = new Upload().execute(null,null,null);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void openProgress(String title, String content) {
        progressDialog = new SweetAlertDialog(current_activity, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(title);
        progressDialog.setContentText(content);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    public void closeProgressUtils() {
        progressDialog.dismiss();
    }

    class Upload extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... unused){
            Map config = new HashMap();
            config.put("cloud_name", Constants.CLOUD_NAME);
            config.put("api_key", Constants.API_KEY);
            config.put("api_secret", Constants.API_SECRET);
            Cloudinary mobileCloudinary = new Cloudinary(config);
            String returnData = "";
            Bitmap myBitmap = null;
            File imageFile = new File(selected_path);
            try {
                if(imageFile.exists()){
                    myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                }
                String file_name = "compress_"+selected_path.substring(selected_path.lastIndexOf("/")+1);
                String pathFile = selected_path.substring(0,selected_path.lastIndexOf("/")+1);

                File data = new File(pathFile+file_name);
                FileOutputStream out = new FileOutputStream(data);
                myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
                out.flush();
                out.close();

                String folder = "";
                if(type.equals("sipp")){
                    folder = "SIPP-Nurse/";
                    Map uploadResult = mobileCloudinary.uploader().upload(pathFile+file_name, ObjectUtils.asMap("folder",folder));
                    String secure_url = uploadResult.get("secure_url").toString();
                    returnData = secure_url ;
                    UserProfile userData = new UserProfile();
                    userData.setId(homecareSessionManager.getUserDetail().getId());
                    userData.setSippUrl(secure_url);
                    postData(userData, type);
                }
                else if(type.equals("str")){
                    folder = "STR-Nurse/";
                    Map uploadResult = mobileCloudinary.uploader().upload(pathFile+file_name, ObjectUtils.asMap("folder",folder));
                    String secure_url = uploadResult.get("secure_url").toString();
                    returnData = secure_url ;
                    UserProfile userData = new UserProfile();
                    userData.setId(homecareSessionManager.getUserDetail().getId());
                    userData.setRegisterNurseNumberUrl(secure_url);
                    postData(userData, type);
                }
                else if(type.equals("profile_nurse")){
                    folder = "Profile_Picture_Nurse/";
                    Map uploadResult = mobileCloudinary.uploader().upload(pathFile+file_name, ObjectUtils.asMap("folder",folder));
                    String secure_url = uploadResult.get("secure_url").toString();
                    returnData = secure_url ;
                    UserProfile userData = new UserProfile();
                    userData.setId(homecareSessionManager.getUserDetail().getId());
                    userData.setPhotoPath(secure_url);
                    postData(userData, type);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Error nya : "+ e.getLocalizedMessage());
            }
            return returnData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openProgress("Uploading ...","Proses Upload Foto");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            closeProgressUtils();
            Log.i(TAG, "Upload finished");
        }

        public void postData(final UserProfile registerParam, final String type) throws UnsupportedEncodingException {
            final Call<ResponseBody> resp = userAPIInterface.updateProfile(registerParam.getId(), registerParam);
            resp.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.i(TAG, response.code() + " Error");
                    if (response.code() == 200) {
                        if(type.equals("str")){
                            Toast.makeText(current_activity, "Foto STR telah berhasil diupload", Toast.LENGTH_LONG).show();
                            imageAttachment_callBack.image_attachment(0, null, null,null, registerParam.getRegisterNurseNumberUrl());
                        }
                        else if(type.equals("sipp")){
                            Toast.makeText(current_activity, "Foto SIPP telah berhasil diupload", Toast.LENGTH_LONG).show();
                            imageAttachment_callBack.image_attachment(0, null, null,null, registerParam.getSippUrl());
                        }
                        else if(type.equals("profile_nurse")){
                            Toast.makeText(current_activity, "Foto Profil anda telah berhasil diupload", Toast.LENGTH_LONG).show();
                            imageAttachment_callBack.image_attachment(0, null, null,null, registerParam.getPhotoPath());
                        }
                    } else {
                        if(type.equals("str")){
                            Toast.makeText(current_activity, "Foto STR gagal diupload / diunggah !", Toast.LENGTH_LONG).show();
                        }
                        else if(type.equals("sipp")){
                            Toast.makeText(current_activity, "Foto SIPP gagal diupload / diunggah !", Toast.LENGTH_LONG).show();
                        }
                        else if(type.equals("profile_nurse")){
                            Toast.makeText(current_activity, "Foto profile gagal diupload / diunggah !", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i(TAG, "Failed");
                    Toast.makeText(current_activity, "Foto STR gagal diupload / diunggah !", Toast.LENGTH_LONG).show();
                    call.cancel();
                }
            });
        }

    }

    /**
     *
     *
     * @param file
     * @param bmp
     */
    public void store_image(File file, Bitmap bmp)
    {
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 80, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Image Attachment Callback

    public interface ImageAttachmentListener {
        public void image_attachment(int from, String filename, Bitmap file, Uri uri, String url);
    }

}
