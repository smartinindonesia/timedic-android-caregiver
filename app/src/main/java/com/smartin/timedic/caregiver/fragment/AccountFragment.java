package com.smartin.timedic.caregiver.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.firebase.auth.FirebaseAuth;
import com.smartin.timedic.caregiver.AccountSettingActivity;
import com.smartin.timedic.caregiver.ChangePasswordActivity;
import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.config.Constants;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.AppSetting;

import com.facebook.login.LoginManager;


/**
 * Created by Hafid on 8/22/2017.
 */


public class AccountFragment extends Fragment {

    public static String TAG = "[AccountFragment]";

    @BindView(R.id.accountLayout)
    LinearLayout accountLayout;
    @BindView(R.id.btnLogout)
    LinearLayout logoutBtn;
    @BindView(R.id.btnRateApp)
    LinearLayout btnRateApp;
    @BindView(R.id.accountSetting)
    TextView accountSetting;
    @BindView(R.id.btnChangePassword)
    LinearLayout btnChangePassword;
    @BindView(R.id.btnPushNotification)
    RelativeLayout btnPushNotif;
    @BindView(R.id.turnNotification)
    Switch notifToggle;
    @BindView(R.id.btnPrivacy)
    LinearLayout btnPrivacy;
    @BindView(R.id.btnTermAndCond)
    LinearLayout btnTermAndCond;

    private HomecareSessionManager homecareSessionManager;
    private AppSetting appSetting;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homecareSessionManager = new HomecareSessionManager(getActivity(), getContext());
        appSetting = homecareSessionManager.getSetting();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, newView);
        if (appSetting.getActive()) {
            notifToggle.setChecked(true);
        } else {
            notifToggle.setChecked(false);
        }
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homecareSessionManager.logout();
                mAuth.signOut();
                LoginManager.getInstance().logOut();
            }
        });
        accountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AccountSettingActivity.class);
                startActivity(intent);
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        notifToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                appSetting.setActive(b);
                homecareSessionManager.setSetting(appSetting);
                if (b) {
                    Snackbar.make(accountLayout, "Notifikasi diaktifkan!", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(accountLayout, "Notifikasi dimatikan!", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        btnRateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMarketPlace();
            }
        });
        btnPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(Constants.PRIVACY_STATEMENT);
            }
        });

        btnTermAndCond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUrl(Constants.TERM_AND_COND);
            }
        });
        return newView;
    }

    private void gotoMarketPlace() {
        Uri uri = Uri.parse("market://details?id=" + getContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())));
        }
    }

    private void openUrl(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
