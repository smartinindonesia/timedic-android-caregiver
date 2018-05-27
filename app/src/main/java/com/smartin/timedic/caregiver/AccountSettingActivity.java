package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.smartin.timedic.caregiver.adapter.GenderSpinnerAdapter;
import com.smartin.timedic.caregiver.adapter.ReligionAdapter;
import com.smartin.timedic.caregiver.config.ConstantVals;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.GenderOption;
import com.smartin.timedic.caregiver.model.Religion;
import com.smartin.timedic.caregiver.model.User;

import com.smartin.timedic.caregiver.model.parammodel.UserProfile;
import com.smartin.timedic.caregiver.tools.ConverterUtility;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.UserAPIInterface;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountSettingActivity extends AppCompatActivity implements Imageutils.ImageAttachmentListener {
    public static final String TAG = "[AccountSettingAct]";

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.firstName)
    EditText firstName;
    @BindView(R.id.middleName)
    EditText middleName;
    @BindView(R.id.lastName)
    EditText lastName;
    @BindView(R.id.phoneUser)
    EditText phone;
    @BindView(R.id.btnEdit)
    Button btnEdit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.emailAddress)
    TextView emailAddress;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.selectDOB)
    ImageButton selectDob;
    @BindView(R.id.dateOfBirth)
    EditText dob;
    @BindView(R.id.genderSpin)
    Spinner genderSpin;
    @BindView(R.id.profPic)
    ImageView profPic;
    @BindView(R.id.religionName)
    Spinner religionName;
    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.usernameTitle)
    TextView usernameTitle;
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
    @BindView(R.id.dateOfBirthTitle)
    TextView dateOfBirthTitle;
    @BindView(R.id.genderSpinTitle)
    TextView genderSpinTitle;
    @BindView(R.id.religionNameText)
    TextView religionNameText;
    @BindView(R.id.addressTitle)
    TextView addressTitle;

    @BindView(R.id.textUploadFoto)
    TextView textUploadFoto;

    GenderSpinnerAdapter adapterGender;
    List<GenderOption> genderOptions;

    ReligionAdapter religionAdapter;
    List<Religion> religionList;

    Imageutils imageutils;

    private DatePickerDialog datePickerDialog;
    private UserAPIInterface userAPIInterface;
    private HomecareSessionManager homecareSessionManager;
    private User user;
    private SweetAlertDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        ButterKnife.bind(this);
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        userAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(UserAPIInterface.class);
        createTitleBar();
        user = homecareSessionManager.getUserDetail();
        imageutils = new Imageutils(this, homecareSessionManager, userAPIInterface);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doEditProfile();
            }
        });
        selectDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH) + 2;
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(AccountSettingActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih tanggal pelayanan");
                datePickerDialog.show();
                dob.setText(day + "-" + (month + 1) + "-" + year);
            }
        });

        textUploadFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Masih dikerjakan", Toast.LENGTH_LONG).show();
                imageutils.imagepicker(1,"profile_nurse");
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

        genderOptions = ConstantVals.getGenders();
        adapterGender = new GenderSpinnerAdapter(this, this, genderOptions);
        genderSpin.setAdapter(adapterGender);

        religionList = ConstantVals.getReligionList();
        religionAdapter = new ReligionAdapter(this, this, religionList);
        religionName.setAdapter(religionAdapter);

        getUserDetail();
        setFonts();
    }

    public void getUserDetail() {
        final Call<User> resp = userAPIInterface.getProfile(user.getId());
        resp.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                homecareSessionManager.updateProfile(user);
                fillTheForm();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                fillTheForm();
            }
        });
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        emailAddress.addTextChangedListener(new TextWatcher() {
            @TargetApi(Build.VERSION_CODES.M)
            public void afterTextChanged(Editable s) {
                String email = emailAddress.getText().toString().trim();
                if (s.length() > 0) {
                    if (email.matches(emailPattern)) {
                        emailAddress.setBackground(getDrawable(R.drawable.bg_green_rounded_textfield));
                        emailAddress.setTextColor(getColor(R.color.btn_on_text));
                    } else {
                        emailAddress.setBackground(getDrawable(R.drawable.bg_red_rounded_textfield));
                        emailAddress.setTextColor(getColor(R.color.btn_on_text));
                    }
                } else {
                    emailAddress.setBackground(getDrawable(R.drawable.bg_gray_rounded_textfield));
                    emailAddress.setTextColor(getColor(R.color.text_color));
                }
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
                if (s.length() > 0) {
                    if (android.util.Patterns.PHONE.matcher(num).matches()) {
                        phone.setBackground(getDrawable(R.drawable.bg_green_rounded_textfield));
                        phone.setTextColor(getColor(R.color.btn_on_text));
                    } else {
                        phone.setBackground(getDrawable(R.drawable.bg_red_rounded_textfield));
                        phone.setTextColor(getColor(R.color.btn_on_text));
                    }
                } else {
                    phone.setBackground(getDrawable(R.drawable.bg_gray_rounded_textfield));
                    phone.setTextColor(getColor(R.color.text_color));
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

    public void fillTheForm() {
        username.setText(user.getUsername());
        username.setEnabled(false);
        firstName.setText(user.getFrontName());
        middleName.setText(user.getMiddleName());
        lastName.setText(user.getLastName());
        phone.setText(user.getPhoneNumber());
        emailAddress.setText(user.getEmail());
        address.setText(user.getAddress());
        dob.setText(ConverterUtility.getDateString(user.getDateBirth()));
        if (user.getPhotoPath() != null) {
            if (!user.getPhotoPath().equals("")) {
                setGlide(user.getPhotoPath());
                profPic.setVisibility(View.VISIBLE);
            }
        } else {
            profPic.setVisibility(View.GONE);
        }
        if (user.getGender() != null) {
            if (user.getGender().equals("Laki-Laki")) {
                genderSpin.setSelection(0);
            } else {
                genderSpin.setSelection(1);
            }
        } else {
            genderSpin.setSelection(0);
        }
        if (user.getReligion() != null) {
            if (user.getReligion().equals("Islam")) {
                religionName.setSelection(0);
            } else if (user.getReligion().equals("Kristen")) {
                religionName.setSelection(1);
            } else if (user.getReligion().equals("Katolik")) {
                religionName.setSelection(2);
            } else if (user.getReligion().equals("Hindu")) {
                religionName.setSelection(3);
            } else if (user.getReligion().equals("Budha")) {
                religionName.setSelection(4);
            } else if (user.getReligion().equals("Kong Hu Cu")) {
                religionName.setSelection(5);
            } else {
                religionName.setSelection(6);
            }
        } else {
            religionName.setSelection(6);
        }
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
        finish();
        onBackPressed();
        return true;
    }

    private void doEditProfile() {
        UserProfile registerParam = new UserProfile();
        registerParam.setId(user.getId());
        registerParam.setFrontName(firstName.getText().toString());
        registerParam.setLastName(lastName.getText().toString());
        registerParam.setMiddleName(middleName.getText().toString());
        registerParam.setPhoneNumber(phone.getText().toString());
        registerParam.setEmail(emailAddress.getText().toString());
        registerParam.setAddress(address.getText().toString());
        Long dobs = ConverterUtility.getTimeStamp(dob.getText().toString(), "dd-MM-yyyy");
        registerParam.setDateBirth(dobs);
        registerParam.setGender(genderSpin.getSelectedItem().toString());
        registerParam.setReligion(((Religion) religionName.getAdapter().getItem(religionName.getSelectedItemPosition())).getReligion());
        if (registerParam.isValidPhone()) {
            try {
                postData(registerParam);
            } catch (UnsupportedEncodingException e) {
                Toast.makeText(getApplicationContext(), "Parameter tidak benar!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Nomor HP tidak valid!", Toast.LENGTH_LONG).show();
        }
    }

    private void postData(UserProfile registerParam) throws UnsupportedEncodingException {
        final Call<ResponseBody> resp = userAPIInterface.updateProfile(registerParam.getId(), registerParam);
        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, response.code() + " Error");
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Profil telah diubah!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Profil gagal diubah!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed");
                Toast.makeText(getApplicationContext(), "Profil gagal diubah!", Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    private void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(usernameTitle);
        arrayList.add(username);
        arrayList.add(frontNameTitle);
        arrayList.add(firstName);
        arrayList.add(middleNameTitle);
        arrayList.add(middleName);
        arrayList.add(lastName);
        arrayList.add(lastNameTitle);
        arrayList.add(phoneUserTitle);
        arrayList.add(phone);
        arrayList.add(emailAddress);
        arrayList.add(emailAddressTitle);
        arrayList.add(dateOfBirthTitle);
        arrayList.add(dob);
        arrayList.add(genderSpinTitle);
        arrayList.add(btnEdit);
        arrayList.add(religionNameText);
        arrayList.add(address);
        arrayList.add(addressTitle);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }

    public void setGlide(String url) {
        openProgress("Sinkronisasi", "Proses Sinkronisasi dengan server !");
        Glide.with(getApplicationContext()).load(url).apply(new RequestOptions()
                .circleCropTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
                .thumbnail(0.5f)
                .listener(new RequestListener<Drawable>() {
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
                })
                .into(profPic);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageutils.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void image_attachment(int from, String filename, Bitmap file, Uri uri, String url) {
        setGlide(url);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        imageutils.request_permission_result(requestCode, permissions, grantResults);
    }
}
