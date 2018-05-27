package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
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
import com.smartin.timedic.caregiver.tools.restservice.UserAPIInterface;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UploadSippActivity extends AppCompatActivity implements Imageutils.ImageAttachmentListener{


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btnSelectPhotoSIPP)
    Button btnSelectPhotoSIPP;

    @BindView(R.id.viewImageSIPP)
    ImageView imagePhotoSIPP;

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
        setContentView(R.layout.activity_upload_sipp);

        ButterKnife.bind(this);
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        userAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(UserAPIInterface.class);
        imageutils = new Imageutils(this, homecareSessionManager, userAPIInterface);
        createTitleBar();
        setFonts();

        btnSelectPhotoSIPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageutils.imagepicker(1,"sipp");
            }
        });

        loadImage("sipp");

    }

    public void loadImage(String type) {

        final Call<ResponseBody> resp = userAPIInterface.getUrlById(homecareSessionManager.getUserDetail().getId().toString(), type);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {
                    String dataURL = null;
                    try {
                        dataURL = response.body().string();
                        if (!dataURL.equals("") || dataURL != null) {
                            setGlide(dataURL);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "File SIPP anda masih kosong, silahkan upload SIPP anda !", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Gagal mendapatkan url foto !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed");
                Toast.makeText(getApplicationContext(), "Gagal mendapatkan url foto !", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void setGlide(String url) {
        openProgress("Sinkronisasi","Proses Sinkronisasi dengan server !");
        Glide.with(this).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                closeProgress();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                closeProgress();
                return false;
            }
        }).into(imagePhotoSIPP);
        imagePhotoSIPP.setBackgroundColor(ContextCompat.getColor(this, R.color.theme_blue));

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


    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        //ArrayList<TextView> arrayListB = new ArrayList<>();
        arrayList.add(btnSelectPhotoSIPP);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
        //ViewFaceUtility.applyFonts(arrayListB, this, "fonts/Dosis-ExtraBold.otf");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri, String url) {
        //this.bitmap = file;
        //this.file_name = filename;
        setGlide(url);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
