package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.ContactRes;
import com.smartin.timedic.caregiver.model.User;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.ContactUsAPIInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.contactTitle)
    TextView contactTitle;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.orTitle)
    TextView orTitle;
    @BindView(R.id.contact2)
    TextView contact2;
    @BindView(R.id.emailContact)
    TextView emailContact;

    private HomecareSessionManager homecareSessionManager;
    private ContactRes contactRes;
    private ContactUsAPIInterface contactUsAPIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());
        contactUsAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(ContactUsAPIInterface.class);
        createTitleBar();
        setFonts();
        getContactDetail();
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

    private void setFonts(){
        ArrayList<TextView> arrayList = new ArrayList<>();
        ArrayList<TextView> arrayListB = new ArrayList<>();
        arrayList.add(contactTitle);
        arrayList.add(orTitle);
        arrayList.add(emailContact);
        arrayListB.add(contact);
        arrayListB.add(contact2);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
        ViewFaceUtility.applyFonts(arrayListB, this, "fonts/Dosis-ExtraBold.otf");
    }
}
