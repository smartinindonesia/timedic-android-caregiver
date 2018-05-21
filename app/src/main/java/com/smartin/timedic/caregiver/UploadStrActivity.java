package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.ContactRes;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.ContactUsAPIInterface;
import com.smartin.timedic.caregiver.tools.restservice.UserAPIInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UploadStrActivity extends AppCompatActivity implements Imageutils.ImageAttachmentListener, Imageutils.openWindowsProgressListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btnSelectPhotoSTR)
    Button btnSelectPhotoSTR;

    @BindView(R.id.viewImageSTR)
    ImageView imagePhotoSTR;

    private Bitmap bitmap;
    private String file_name;

    Imageutils imageutils;

    private HomecareSessionManager homecareSessionManager;
    private ContactRes contactRes;
    private UserAPIInterface userAPIInterface;

    private SweetAlertDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_sipp_str);

        ButterKnife.bind(this);
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        userAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(UserAPIInterface.class);
        imageutils = new Imageutils(this, homecareSessionManager, userAPIInterface);
        createTitleBar();
        setFonts();
        //getContactDetail();

        btnSelectPhotoSTR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectImage();
                //openProgress("Loading ...","Membaca foto dari server");
                imageutils.imagepicker(1);
                //loadImage("str");
            }
        });


        loadImage("str");
        //closeProgress();
    }

    public void loadImage(String type){

        final Call<ResponseBody> resp = userAPIInterface.getUrlById(homecareSessionManager.getUserDetail().getId().toString(), type);
        //openProgress("Loading ...","Membaca foto dari server");
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                //Log.i(TAG, response.code() + " Error");
                if (response.code() == 200) {
                    //Toast.makeText(getApplicationContext(), "Profil telah diubah!", Toast.LENGTH_LONG).show();
                    String dataURL = null;
                    try {
                        dataURL = response.body().string();
                        setGlide(dataURL);
                        //closeProgress();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    //System.out.println("Ini dia : "+dataURL);

                } else {
                    Toast.makeText(getApplicationContext(), "Gagal mendapatkan url foto !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed");
                Toast.makeText(getApplicationContext(), "Gagal mendapatkan url foto !", Toast.LENGTH_LONG).show();
                call.cancel();
                //closeProgress();
            }
        });

        //closeProgress();
    }

    public void setGlide(String url){
        //closeProgress();

        Glide.with(this).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;

            }
        }).into(imagePhotoSTR);
    }

    private void openProgress(String title, String content) {
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(title);
        progressDialog.setContentText(content);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    private void closeProgress() {
        progressDialog.dismiss();
    }

    /*
    private void selectImage() {
        final CharSequence[] options = { "Dari Kamera HP", "Dari Galeri HP","Batal" };
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadStrActivity.this);

        builder.setTitle("Pilih File STR Anda");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Dari Kamera HP")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Dari Galeri HP")){

                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    */

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }

                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),bitmapOptions);

                    imagePhotoSTR.setImageBitmap(bitmap);

                    String path = android.os.Environment.getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();

                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    catch (IOException e) {
                        e.printStackTrace();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                //Log.d("path of image from gallery......******************.........", picturePath+"");
                imagePhotoSTR.setImageBitmap(thumbnail);
            }
        }
    }
    */


    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this, "fonts/BalooBhaina-Regular.ttf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }

    /*
    public void getContactDetail() {
        final Call<ContactRes> resp = contactUsAPIInterface.getContactDetails((long)1);
        resp.enqueue(new Callback<ContactRes>() {
            @Override
            public void onResponse(Call<ContactRes> call, Response<ContactRes> response) {
                contactRes = response.body();
                fillTheForm();
            }

            @Override
            public void onFailure(Call<ContactRes> call, Throwable t) {
                fillTheForm();
            }
        });
    }

    public void fillTheForm(){
        contact.setText(contactRes.getPhoneOffice());
        contact2.setText(contactRes.getMobilePhone());
        emailContact.setText("email : "+contactRes.getEmail());
    }
    */

    private void setFonts(){
        ArrayList<TextView> arrayList = new ArrayList<>();
        ArrayList<TextView> arrayListB = new ArrayList<>();
        /*
        arrayList.add(contactTitle);
        arrayList.add(orTitle);
        arrayList.add(emailContact);
        arrayListB.add(contact);
        arrayListB.add(contact2);
        */
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
        ViewFaceUtility.applyFonts(arrayListB, this, "fonts/Dosis-ExtraBold.otf");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri, String url) {
        this.bitmap=file;
        this.file_name=filename;
        //openProgress("Loading ...","Proses Upload Foto");
        //imagePhotoSTR.setImageBitmap(file);
        //loadImage("str");
        setGlide(url);
        //closeProgress();
        //String path =  Environment.getExternalStorageDirectory() + File.separator + "ImageAttach" + File.separator;
        System.out.println("URL nya bro "+url);
        //imageutils.createImage(file,filename,path,false);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //loadImage("str");
        //closeProgress();
    }


    @Override
    public void open() {
        openProgress("Loading ...","Proses Upload Foto");
    }
}
