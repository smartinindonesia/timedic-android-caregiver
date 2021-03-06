package com.smartin.timedic.caregiver;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.shaishavgandhi.loginbuttons.FacebookButton;
import com.shaishavgandhi.loginbuttons.GooglePlusButton;
import com.smartin.timedic.caregiver.config.PermissionConst;
import com.smartin.timedic.caregiver.config.RequestCode;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.User;
import com.smartin.timedic.caregiver.model.responsemodel.LoginResponse;
import com.smartin.timedic.caregiver.tools.AesUtil;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.UserAPIInterface;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "[LoginActivity]";

    @BindView(R.id.btnSignIn)
    Button signIn;

    @BindView(R.id.emailAddress)
    EditText emailAddress;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.mainLayout)
    CoordinatorLayout mainLayout;

    @BindView(R.id.btnSignup)
    Button signUp;

    @BindView(R.id.btnGoogleSignIn)
    ImageButton btnGoogleSignIn;

    @BindView(R.id.btnFacebook)
    ImageButton facebookButton;

    @BindView(R.id.lupaPassword)
    TextView lupaPassword;

    @BindView(R.id.orWithText)
    TextView orWithText;

    @BindView(R.id.dontHaveAccount)
    TextView dontHaveAccount;

    private CallbackManager mCallbackManager;

    private FirebaseAuth mAuth;

    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;

    private SharedPreferences mSharedPreferences;

    private String fullName, email, urlPhoto;

    private UserAPIInterface userAPIInterface;

    private HomecareSessionManager homecareSessionManager;
    private User user;
    private boolean checkPermission = false;
    private SweetAlertDialog progressDialog;

    private static final int MY_PERMISSIONS_REQUEST = 999;
    final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

        if (homecareSessionManager.isLogin()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

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

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals("") && emailAddress.getText().toString().equals("")){
                    Snackbar.make(mainLayout, "Silahkan isi email dan password akun anda !", Snackbar.LENGTH_LONG).show();
                }
                else if(!emailAddress.getText().toString().matches(emailPattern)){
                    Snackbar.make(mainLayout, "Email anda tidak valid !", Snackbar.LENGTH_LONG).show();
                }
                else{
                    cekMethod(emailAddress.getText().toString());
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newinten = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(newinten);
            }
        });

        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        setPermission();
        facebookButton();
        googleLoginInit();
        setFonts();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                    if(currentUser.getProviders().get(0).equals("google.com")){
                                        doLoginFirebase(currentUser, "google", idToken);
                                    }
                                    else if(currentUser.getProviders().get(0).equals("facebook.com")){
                                        doLoginFirebase(currentUser, "facebook", idToken);
                                    }
                                    else if(currentUser.getProviders().get(0).equals("password")){
                                        doLoginFirebase(currentUser, "email", idToken);
                                    }
                                // ...
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
        }
        else {
            //back to login
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RequestCode.REQUEST_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
            }
        }
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void cekMethod(String email){
        Call<ResponseBody> responseCall = userAPIInterface.checkCaregiverPasswordIsNullOrNot(email);

        responseCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        String dataIndikator = response.body().string();

                        if(dataIndikator.equals("false")){
                            doLoginEmail();
                        }
                        else if(dataIndikator.equals("")){
                            Snackbar.make(mainLayout, "Email tidak terdaftar !", Snackbar.LENGTH_LONG).show();
                        }
                        else{
                            if(dataIndikator.substring(5).equals("f")){
                                facebookButton.performClick();
                            }
                            else{
                                btnGoogleSignIn.performClick();
                            }
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

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        openProgress("Loading...", "Proses verifikasi!");
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                closeProgress();

                if (!task.isSuccessful()) {
                    Log.w(TAG, "signInWithCredential", task.getException());Toast.makeText(getApplicationContext(), "Autentikasi google gagal!", Toast.LENGTH_LONG).show();
                }
                else {
                    final FirebaseUser user = mAuth.getCurrentUser();
                    //GetTokenResult idTokenResult = user.getIdToken(false).getResult();
                    user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                if (user != null) {
                                    // User is signed in
                                    doLoginFirebase(user, "google",idToken);
                                    Log.d(TAG, "onAuthStateChanged:signed_in:" + idToken);

                                } else {
                                    // User is signed out
                                    Log.d(TAG, "onAuthStateChanged:signed_out");
                                }                    // ...
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
                }
            }
        });
    }

    public void doLoginFirebase(final FirebaseUser user, final String type, final String tokenId) {

        openProgress("Loading...", "Proses Login!");

        Call<LoginResponse> responseCall = userAPIInterface.loginUserWithFirebaseToken(tokenId, type);
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
                    gotoFirebaseSignUpPage(user, type);
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

    private void gotoFirebaseSignUpPage(FirebaseUser fbaseuser, String type) {
        User user = new User();
        String name[] = fbaseuser.getDisplayName().split(" ");
        String elaborateLastName = "";
        for (int i = 0; i < name.length; i++) {
            if (i == 0) {
                user.setFrontName(name[i]);
            } else if (i == 1) {
                user.setMiddleName(name[i]);
            } else if (i > 1) {
                elaborateLastName = elaborateLastName + " " + name[i];
                user.setLastName(elaborateLastName);
            }
        }
        user.setPhotoPath(fbaseuser.getPhotoUrl().toString());
        user.setPhoneNumber(fbaseuser.getPhoneNumber());
        user.setEmail(fbaseuser.getEmail());
        if(type.equals("google")){
            user.setFirebaseIdGoogle(fbaseuser.getUid());
        }
        else if(type.equals("facebook")){
            user.setFirebaseIdFacebook(fbaseuser.getUid());
        }
        Intent intent = new Intent(this, FUserSignUpActivity.class);
        intent.putExtra("fbase_user", user);
        startActivity(intent);
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
                    final FirebaseUser user = mAuth.getCurrentUser();
                    //GetTokenResult idTokenResult = user.getIdToken(false).getResult();
                    //Log.d("User ID nya ", user.getUid());

                    user.getIdToken(true)
                            .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                public void onComplete(@NonNull Task<GetTokenResult> task) {
                                    if (task.isSuccessful()) {
                                        String idToken = task.getResult().getToken();
                                        if (user != null) {
                                            // User is signed in
                                            //doLoginFirebase(user, "google",idToken);
                                            doLoginFirebase(user, "facebook", idToken);
                                            facebookButton.setEnabled(true);
                                            Log.d(TAG, "onAuthStateChanged:signed_in:" + idToken);

                                        } else {
                                            // User is signed out
                                            Log.d(TAG, "onAuthStateChanged:signed_out");
                                        }
                                        // ...
                                    } else {
                                        // Handle error -> task.getException();
                                    }
                                }
                            });

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                    facebookButton.setEnabled(true);

                    //updateUI();
                }
            }
        });
    }

    private void initSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        fullName = mSharedPreferences.getString("name", "");
        email = mSharedPreferences.getString("email", "");
        urlPhoto = mSharedPreferences.getString("urlPhoto", "");
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
        Call<LoginResponse> responseCall = userAPIInterface.loginUser(emailAddress.getText().toString(), shahex);
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

    public void doLoginEmail() {
        openProgress("Loading...", "Proses Login!");
        String shahex = AesUtil.Encrypt(password.getText().toString().trim());
        Log.d(TAG, "Hasil enkripsi : " + shahex);
        mAuth.signInWithEmailAndPassword(emailAddress.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    //error loging
                    Toast.makeText(LoginActivity.this, "Email atau password tidak sesuai "+task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    closeProgress();
                }
                else{

                    final FirebaseUser user = mAuth.getCurrentUser();
                    //GetTokenResult idTokenResult = user.getIdToken(false).getResult();
                    user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                if (user != null) {
                                    //user.getProviders().get(0);
                                    //Log.d(TAG, "Ini dia : " + user.getProviders().get(0));
                                    doLoginFirebase(user, "email", idToken);
                                    Log.d(TAG, "onAuthStateChanged:signed_in:" + idToken);

                                } else {
                                    // User is signed out
                                    Log.d(TAG, "onAuthStateChanged:signed_out");
                                }
                                // ...
                            } else {
                                // Handle error -> task.getException();
                            }
                        }
                    });
                }
            }
        });
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

    public void gotoMainPage(User usr, String token) {
        homecareSessionManager.createLoginSession(usr, token);
        Log.i(TAG, "Lewat sini " + usr);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void googleLoginInit() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth = FirebaseAuth.getInstance();
        googleButton();
    }

    private void googleButton(){
        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    signIn();
                    Log.d(TAG, "Sign in ");
                } else {
                    signOut();
                    Log.d(TAG, "Signed Out ");
                }
            }
        });
    }

    private void facebookButton(){
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

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RequestCode.REQUEST_GOOGLE_SIGN_IN);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                    }
                });
    }

    private void setFonts(){
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(signIn);
        arrayList.add(signUp);
        arrayList.add(emailAddress);
        arrayList.add(password);
        arrayList.add(lupaPassword);
        arrayList.add(orWithText);
        arrayList.add(dontHaveAccount);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }
}
