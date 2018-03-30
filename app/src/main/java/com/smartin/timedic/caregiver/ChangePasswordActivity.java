package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.User;
import com.smartin.timedic.caregiver.model.parammodel.PasswordProfile;

import com.smartin.timedic.caregiver.tools.AesUtil;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.UserAPIInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    public static final String TAG = "[ChangePasswordAct]";

    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.rePassword)
    EditText retypePassword;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    private UserAPIInterface userAPIInterface;
    private HomecareSessionManager homecareSessionManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        userAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(UserAPIInterface.class);
        createTitleBar();
        user = homecareSessionManager.getUserDetail();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doUpdatePassword();
            }
        });
        retypePassword.addTextChangedListener(new TextWatcher() {
            @TargetApi(Build.VERSION_CODES.M)
            public void afterTextChanged(Editable s) {
                String pass = password.getText().toString();
                String rePass = retypePassword.getText().toString();
                if (s.length() > 0) {
                    if (pass.equals(rePass)) {
                        retypePassword.setBackground(getDrawable(R.drawable.bg_green_rounded_textfield));
                        retypePassword.setTextColor(getColor(R.color.btn_on_text));
                    } else {
                        retypePassword.setBackground(getDrawable(R.drawable.bg_red_rounded_textfield));
                        retypePassword.setTextColor(getColor(R.color.btn_on_text));
                    }
                } else {
                    retypePassword.setBackground(getDrawable(R.drawable.edittext_border));
                    retypePassword.setTextColor(getColor(R.color.text_color));
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this, "fonts/Dosis-Bold.otf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void doUpdatePassword() {
        Log.i(TAG, password.getText().toString());
        PasswordProfile registerParam = new PasswordProfile();
        String shahex = AesUtil.Encrypt(password.getText().toString());
        registerParam.setPassword(shahex);
        if (!password.getText().toString().trim().equals("")) {
            if (password.getText().toString().equals(retypePassword.getText().toString())) {
                postData(registerParam);
            } else {
                Toast.makeText(getApplicationContext(), "Password tidak sesuai, harap diulang kembali!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Password tidak boleh kosong!", Toast.LENGTH_LONG).show();
        }
    }

    public void postData(PasswordProfile pass) {
        Call<ResponseBody> resp = userAPIInterface.updatePassword(user.getId(), pass);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Password berhasil diganti!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Penggantian password baru gagal!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                homecareSessionManager.logout();
            }
        });
    }
}
