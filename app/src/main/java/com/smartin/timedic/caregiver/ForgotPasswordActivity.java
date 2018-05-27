package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.UserAPIInterface;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.emailAddress)
    EditText emailAddress;

    @BindView(R.id.forgotPasswordTitle)
    TextView forgotPasswordTitle;

    @BindView(R.id.btnResetPassword)
    Button btnResetPassword;

    @BindView(R.id.txtNotYetHaveAccount)
    TextView txtNotYetHaveAccount;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    FirebaseAuth mAuth;

    private UserAPIInterface userAPIInterface;

    private HomecareSessionManager homecareSessionManager;

    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

        createTitleBar();

        userAPIInterface = APIClient.getClient().create(UserAPIInterface.class);
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        setFonts();

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailAddress.getText().toString().trim();

                if(email.equals("") || email.equals(null)){
                    Toast.makeText(getApplicationContext(), "Alamat email harus diisi !", Toast.LENGTH_LONG).show();
                }
                else if(!email.matches(emailPattern)){
                    Snackbar.make(mainLayout, "Email anda tidak valid !", Snackbar.LENGTH_LONG).show();
                }
                else{
                    cekMethod(email);
                }
            }
        });

        txtNotYetHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newinten = new Intent(ForgotPasswordActivity.this, SignUpActivity.class);
                startActivity(newinten);
            }
        });

        emailAddress.addTextChangedListener(new TextWatcher() {
            @TargetApi(Build.VERSION_CODES.M)
            public void afterTextChanged(Editable s) {
                String email = emailAddress.getText().toString().trim();
                Integer paddingTop = emailAddress.getPaddingTop();
                Integer paddingBottom = emailAddress.getPaddingBottom();
                Integer paddingLeft = emailAddress.getPaddingLeft();
                Integer paddingRight = emailAddress.getPaddingRight();
                if (s.length() > 0) {
                    if (email.matches(emailPattern)) {
                        emailAddress.setBackground(getDrawable(R.drawable.edittext_border));
                        emailAddress.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black_link_color));
                    } else {
                        emailAddress.setBackground(getDrawable(R.drawable.bg_red_rounded_textfield));
                        emailAddress.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_on_text));
                    }
                } else {
                    emailAddress.setBackground(getDrawable(R.drawable.edittext_border));
                    emailAddress.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color));
                }
                emailAddress.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
    }

    private void cekMethod(final String email){
        Call<ResponseBody> responseCall = userAPIInterface.checkCaregiverPasswordIsNullOrNot(email);
        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        String password = response.body().string();

                        if(password.equals("false")){
                            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Link untuk reset password telah dikirim ke email anda", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "ada masalah dalam proses reset password anda", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else if(password.equals("")){
                            Snackbar.make(mainLayout, "Email tidak terdaftar !", Snackbar.LENGTH_LONG).show();
                        }
                        else if(password.equals("true,g")){
                            //reset password server side for facebook and google
                            Toast.makeText(getApplicationContext(), "email anda telah terhubung dengan akun google anda sehingga tidak bisa direset !", Toast.LENGTH_SHORT).show();
                        }
                        else if(password.equals("true,f")){
                            //reset password server side for facebook and google
                            Toast.makeText(getApplicationContext(), "email anda telah terhubung dengan akun facebook anda sehingga tidak bisa direset !", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Snackbar.make(mainLayout, "Login gagal", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Snackbar.make(mainLayout, getResources().getString(R.string.network_problem), Snackbar.LENGTH_LONG).show();
            }
        });
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

    private void setFonts(){
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(forgotPasswordTitle);
        arrayList.add(txtNotYetHaveAccount);
        arrayList.add(emailAddress);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }
}
