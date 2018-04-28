package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.smartin.timedic.caregiver.adapter.GenderSpinnerAdapter;
import com.smartin.timedic.caregiver.config.Constants;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.GenderOption;
import com.smartin.timedic.caregiver.model.User;
import com.smartin.timedic.caregiver.model.parammodel.RegisterParam;
import com.smartin.timedic.caregiver.tools.AesUtil;
import com.smartin.timedic.caregiver.tools.ConverterUtility;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.UserAPIInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FUserSignUpActivity extends AppCompatActivity {
    public static final String TAG = "[FUserSignUpActivity]";

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.rePassword)
    EditText retypePassword;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.middleName)
    EditText middleName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.phoneUser)
    EditText phone;
    @BindView(R.id.chkAgreement)
    CheckBox checkAgreement;
    @BindView(R.id.signUP)
    Button signUP;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.agreementLink)
    TextView agreementLink;
    @BindView(R.id.emailAddress)
    EditText emailAddress;
    @BindView(R.id.dateOfBirth)
    EditText dob;
    @BindView(R.id.selectDOB)
    ImageButton selectDob;
    @BindView(R.id.genderSpin)
    Spinner genderSpin;

    @BindView(R.id.usernameTitle)
    TextView usernameTitle;
    @BindView(R.id.passwordTitle)
    TextView passwordTitle;
    @BindView(R.id.rePasswordTitle)
    TextView rePasswordTitle;
    @BindView(R.id.firstNameTitle)
    TextView frontNameTitle;
    @BindView(R.id.middleNameTitle)
    TextView middleNameTitle;
    @BindView(R.id.lastNameTitle)
    TextView lastNameTitle;
    @BindView(R.id.phoneUserTitle)
    TextView phoneUserTitle;
    @BindView(R.id.emailAddressTitle)
    TextView emailAddressTitle;
    @BindView(R.id.genderSpinTitle)
    TextView genderSpinTitle;
    @BindView(R.id.dateOfBirthTitle)
    TextView dobTitle;

    GenderSpinnerAdapter adapterGender;
    List<GenderOption> genderOptions;

    private DatePickerDialog datePickerDialog;
    private UserAPIInterface userAPIInterface;
    private User user;

    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;

    private SweetAlertDialog progressDialog;

    private HomecareSessionManager homecareSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuser_sign_up);
        ButterKnife.bind(this);
        userAPIInterface = APIClient.getClient().create(UserAPIInterface.class);
        user = (User) getIntent().getSerializableExtra("fbase_user");
        Log.i(TAG, user.getFrontName());
        googleLoginInit();
        createTitleBar();
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignUpGoogle();
            }
        });
        agreementLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(Constants.TERM_AND_COND);
            }
        });
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
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
                        emailAddress.setBackground(getDrawable(R.drawable.bg_green_rounded_textfield));
                        emailAddress.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_on_text));
                    } else {
                        emailAddress.setBackground(getDrawable(R.drawable.bg_red_rounded_textfield));
                        emailAddress.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_on_text));
                    }
                } else {
                    emailAddress.setBackground(getDrawable(R.drawable.bg_gray_rounded_textfield));
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
        phone.addTextChangedListener(new TextWatcher() {
            @TargetApi(Build.VERSION_CODES.M)
            public void afterTextChanged(Editable s) {
                String num = phone.getText().toString().trim();
                Integer paddingTop = phone.getPaddingTop();
                Integer paddingBottom = phone.getPaddingBottom();
                Integer paddingLeft = phone.getPaddingLeft();
                Integer paddingRight = phone.getPaddingRight();
                if (s.length() > 0) {
                    if (android.util.Patterns.PHONE.matcher(num).matches()) {
                        phone.setBackground(getDrawable(R.drawable.bg_green_rounded_textfield));
                        phone.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_on_text));
                    } else {
                        phone.setBackground(getDrawable(R.drawable.bg_red_rounded_textfield));
                        phone.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_on_text));
                    }
                } else {
                    phone.setBackground(getDrawable(R.drawable.bg_gray_rounded_textfield));
                    phone.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color));
                }
                phone.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });
        selectDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH) + 2;
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(FUserSignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih tanggal pelayanan");
                datePickerDialog.show();
                dob.setText(day + "-" + (month + 1) + "-" + year);
            }
        });
        retypePassword.addTextChangedListener(new TextWatcher() {
            @TargetApi(Build.VERSION_CODES.M)
            public void afterTextChanged(Editable s) {
                String pass = password.getText().toString();
                String rePass = retypePassword.getText().toString();
                Integer paddingTop = retypePassword.getPaddingTop();
                Integer paddingBottom = retypePassword.getPaddingBottom();
                Integer paddingLeft = retypePassword.getPaddingLeft();
                Integer paddingRight = retypePassword.getPaddingRight();
                if (s.length() > 0) {
                    if (pass.equals(rePass)) {
                        retypePassword.setBackground(getDrawable(R.drawable.bg_green_rounded_textfield));
                        retypePassword.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_on_text));
                    } else {
                        retypePassword.setBackground(getDrawable(R.drawable.bg_red_rounded_textfield));
                        retypePassword.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.btn_on_text));
                    }
                } else {
                    retypePassword.setBackground(getDrawable(R.drawable.bg_gray_rounded_textfield));
                    retypePassword.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.text_color));
                }
                retypePassword.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

        genderOptions = new ArrayList<>();
        genderOptions.add(new GenderOption(R.drawable.btn_laki_laki, "Laki-Laki"));
        genderOptions.add(new GenderOption(R.drawable.btn__perempuan, "Perempuan"));
        adapterGender = new GenderSpinnerAdapter(this, this, genderOptions);
        genderSpin.setAdapter(adapterGender);

        fillTheForm();
        setFonts();
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

    public void fillTheForm() {
        firstName.setText(user.getFrontName());
        middleName.setText(user.getMiddleName());
        lastName.setText(user.getLastName());
        phone.setText(user.getPhoneNumber());
        emailAddress.setText(user.getEmail());
        dob.setText(ConverterUtility.getDateString(user.getDateBirth()));
    }

    private void openProgress() {
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText("Loading...");
        progressDialog.setContentText("Proses Login!");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    private void doSignUpGoogle() {
        RegisterParam registerParam = new RegisterParam();
        registerParam.setFirstname(firstName.getText().toString());
        registerParam.setLastname(lastName.getText().toString());
        registerParam.setMiddlename(middleName.getText().toString());
        String shahex = AesUtil.Encrypt(password.getText().toString());
        registerParam.setPassword(shahex);
        registerParam.setUsername(username.getText().toString());
        registerParam.setPhone(phone.getText().toString());
        registerParam.setEmail(emailAddress.getText().toString());
        Long dobs = ConverterUtility.getTimeStamp(dob.getText().toString(), "dd-MM-yyyy");
        registerParam.setDateOfBirth(dobs);
        registerParam.setGender(genderSpin.getSelectedItem().toString());

        if (user.getFirebaseIdGoogle() != null) {
            registerParam.setFirebaseIdGoogle(user.getFirebaseIdGoogle());
        } else if (user.getFirebaseIdFacebook() != null) {
            registerParam.setFirebaseIdFacebook(user.getFirebaseIdFacebook());
        }

        //Log.i(TAG, user.getFirebaseIdGoogle());
        if (retypePassword.getText().toString().equals(password.getText().toString())) {
            if (registerParam.isValidUsername()) {
                if (!registerParam.isUsernameContainSpace()) {
                    if (!registerParam.isFirstNameEmpty()) {
                        if (registerParam.isValidPhone()) {
                            if (registerParam.isValidEmail()) {
                                if (checkAgreement.isChecked()) {
                                    try {
                                        postData(registerParam);
                                    } catch (UnsupportedEncodingException e) {
                                        Toast.makeText(getApplicationContext(), "Parameter tidak benar!", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Anda belum menyetujui pernyataan persetujuan!", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Email tidak valid!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Nomor HP tidak valid!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Nama depan tidak boleh kosong!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Username tidak boleh mengandung spasi!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Username tidak boleh kosong!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Pastikan password anda benar!", Toast.LENGTH_LONG).show();
        }
    }

    private void postData(RegisterParam registerParam) throws UnsupportedEncodingException {

        final Call<ResponseBody> resp = userAPIInterface.registerUser(registerParam);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, response.code() + " Error");
                if (response.code() == 201) {
                    Toast.makeText(getApplicationContext(), "Pendaftaran user baru berhasil dilakukan! Silahkan login untuk melanjutkan", Toast.LENGTH_LONG).show();
                    gotoLogin();
                } else if (response.code() == 401) {
                    try {
                        //Log.i(TAG, response.body().string() + " Respon Body nya d sin");
                        JSONObject object = new JSONObject(response.errorBody().string());
                        Snackbar.make(mainLayout, object.getString("message"), Snackbar.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed");
                call.cancel();
            }
        });
    }

    private void closeProgress() {
        progressDialog.dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        signOut();
        gotoLogin();
        super.onBackPressed();
    }

    private void gotoLogin() {
        Intent intent = new Intent(FUserSignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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

    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        finish();
                    }
                });
        LoginManager.getInstance().logOut();
    }

    private void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(usernameTitle);
        arrayList.add(username);
        arrayList.add(passwordTitle);
        arrayList.add(password);
        arrayList.add(rePasswordTitle);
        arrayList.add(retypePassword);
        arrayList.add(frontNameTitle);
        arrayList.add(firstName);
        arrayList.add(middleNameTitle);
        arrayList.add(middleName);
        arrayList.add(lastNameTitle);
        arrayList.add(lastName);
        arrayList.add(phoneUserTitle);
        arrayList.add(phone);
        arrayList.add(emailAddressTitle);
        arrayList.add(emailAddress);
        arrayList.add(dobTitle);
        arrayList.add(dob);
        arrayList.add(genderSpinTitle);
        arrayList.add(checkAgreement);
        arrayList.add(agreementLink);
        arrayList.add(signUP);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }
}
