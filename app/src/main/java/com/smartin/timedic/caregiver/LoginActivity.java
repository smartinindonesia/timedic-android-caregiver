package com.smartin.timedic.caregiver;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.content.Intent;
import android.view.View;
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

import java.util.Arrays;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "FACELOG";

    private CallbackManager mCallbackManager;

    private FirebaseAuth mAuth;

    private FacebookButton facebookButton;

    private SharedPreferences mSharedPreferences;

    private String fullName, email, urlPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initSharedPreferences();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();


        facebookButton = findViewById(R.id.btnFacebook);

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


}
