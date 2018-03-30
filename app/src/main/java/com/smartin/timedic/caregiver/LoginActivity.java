package com.smartin.timedic.caregiver;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.shaishavgandhi.loginbuttons.FacebookButton;
import com.smartin.timedic.caregiver.config.PermissionConst;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.User;
import com.smartin.timedic.caregiver.model.responsemodel.LoginResponse;
import com.smartin.timedic.caregiver.tools.AesUtil;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.UserAPIInterface;

import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "[LoginActivity]";

    @BindView(R.id.btnSignIn)
    Button signIn;
    @BindView(R.id.emailAddress)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.mainLayout)
    CoordinatorLayout mainLayout;
    @BindView(R.id.btnSignup)
    Button signUp;

    private CallbackManager mCallbackManager;

    private FirebaseAuth mAuth;

    private FacebookButton facebookButton;

    private SharedPreferences mSharedPreferences;

    private String fullName, email, urlPhoto;

    private UserAPIInterface userAPIInterface;

    private HomecareSessionManager homecareSessionManager;
    private User user;
    private boolean checkPermission = false;
    private SweetAlertDialog progressDialog;

    private static final int MY_PERMISSIONS_REQUEST = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initSharedPreferences();

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

        userAPIInterface = APIClient.getClient().create(UserAPIInterface.class);
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        facebookButton = findViewById(R.id.btnFacebook);

        if (homecareSessionManager.isLogin()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Sudah pencet tombol sign in");
                doLogin();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newinten = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(newinten);
            }
        });
        setPermission();




        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                facebookButton.setEnabled(false);

                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {

                        Log.d(TAG, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, "facebook:onError", error);
                        // ...
                    }
                });
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI();
        }
    }

    private void updateUI(){
        Toast.makeText(LoginActivity.this, "You're logged in", Toast.LENGTH_LONG).show();

        Intent profileIntent = new Intent(this, MainActivity.class);
        startActivity(profileIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken Facebook :" + token.getToken());

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();

                    final GetTokenResult idTokenResult = user.getIdToken(false).getResult();

                    Log.d("User ID nya ", user.getUid());


                    /*
                    Log.d("Provider ID nya ", user.getProviderId());
                    Log.d("Namanya ", user.getDisplayName());
                    Log.d("Email ", user.getEmail());
                    //Log.d("Phone ", user.getPhoneNumber());
                    Log.d("Photo ", Objects.toString(user.getPhotoUrl()));
                    Log.d("ID token firebase =", idTokenResult.getToken());

                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("name", user.getDisplayName());
                    editor.putString("email", user.getEmail());
                    editor.putString("urlPhoto", user.getPhotoUrl().toString());
                    editor.apply();
                    */
                    homecareSessionManager.createLoginSession(null, "");

                    facebookButton.setEnabled(true);

                    updateUI();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                    facebookButton.setEnabled(true);

                    updateUI();
                }

                // ...
            }
        });
    }

    @Override
    public void onClick(View view){

    }

    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        fullName = mSharedPreferences.getString("name","");
        email = mSharedPreferences.getString("email","");
        urlPhoto = mSharedPreferences.getString("urlPhoto","");
    }

    private void setPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, PermissionConst.listPermission, MY_PERMISSIONS_REQUEST);
        }
    }

    public void doLogin() {
        openProgress("Loading...", "Proses Login!");

        String shahex = AesUtil.Encrypt(password.getText().toString());
        Call<LoginResponse> responseCall = userAPIInterface.loginUser(username.getText().toString(), shahex);
        responseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                closeProgress();
                if (response.code() == 200) {
                    Log.i(TAG, response.body().getUser().toString());
                    Log.i(TAG, "NEW TOKEN " + response.body().getToken());
                    gotoMainPage(response.body().getUser(), response.body().getToken());
                } else if (response.code() == 401) {
                    Log.i(TAG, response.raw().toString());
                    Snackbar.make(mainLayout, getResources().getString(R.string.login_failed_unauthorized), Snackbar.LENGTH_LONG).show();
                } else if (response.code() == 404) {
                    Snackbar.make(mainLayout, getResources().getString(R.string.login_failed_user_not_found), Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(mainLayout, getResources().getString(R.string.login_err_unknown), Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Snackbar.make(mainLayout, getResources().getString(R.string.network_problem), Snackbar.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void openProgress(String title, String content){
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(title);
        progressDialog.setContentText(content);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    private void closeProgress(){
        progressDialog.dismiss();
    }

    public void gotoMainPage(User usr, String token) {
        homecareSessionManager.createLoginSession(usr, token);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}

