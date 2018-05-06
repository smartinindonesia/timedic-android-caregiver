package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.smartin.timedic.caregiver.adapter.CaregiverHistoryAdapter;
import com.smartin.timedic.caregiver.customuicompt.RecyclerTouchListener;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.CaregiverOrder;
import com.smartin.timedic.caregiver.model.HomecareOrder;
import com.smartin.timedic.caregiver.tools.ConverterUtility;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.HomecareTransactionAPIInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyScheduleDetails extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.serviceName)
    TextView serviceName;

    @BindView(R.id.caregiverHistoryTitle)
    TextView caregiverHistoryTitle;

    @BindView(R.id.infoForReject)
    TextView infoForReject;

    @BindView(R.id.infoForReject2)
    TextView infoForReject2;

    @BindView(R.id.caregiverHistory)
    RecyclerView assignDateAndTime;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    HomecareOrder homecareOrder;
    CaregiverHistoryAdapter caregiverHistoryAdapter;
    HomecareSessionManager homecareSessionManager;
    HomecareTransactionAPIInterface homecareTransactionAPIInterface;

    public void displayDialog(String content, final Long id){
        new MaterialDialog.Builder(this)
                .title(content)
                .content("Apakah anda mau menolak penugasan ini ?")
                .positiveText("Tolak")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        CaregiverOrder dataCaregiverOrder = new CaregiverOrder();
                        dataCaregiverOrder.setAcceptanceStatus(false);
                        updateAcceptanceStatus(id,dataCaregiverOrder);
                        Toast.makeText(getApplicationContext(), "Berhasil membatalkan penugasan ", Toast.LENGTH_LONG).show();

                    }
                })
                .negativeText("Keluar")
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule_details);
        ButterKnife.bind(this);
        createTitleBar();

        homecareOrder = (HomecareOrder) getIntent().getSerializableExtra("schedule_details");

        homecareSessionManager = new HomecareSessionManager(this, this);
        homecareTransactionAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(HomecareTransactionAPIInterface.class);
        loadData();
        setFonts();
    }

    public void loadData(){
        final Call<ArrayList<CaregiverOrder>> resp = homecareTransactionAPIInterface.getScheduleByIdCaregiverAndIdTrx(homecareSessionManager.getUserDetail().getId(), homecareOrder.getId());
        resp.enqueue(new Callback<ArrayList<CaregiverOrder>>() {
            @Override
            public void onResponse(Call<ArrayList<CaregiverOrder>> call, Response<ArrayList<CaregiverOrder>> response) {
                if (response.code() == 200) {
                    setData(response.body());
                }
            }
            @Override
            public void onFailure(Call<ArrayList<CaregiverOrder>> call, Throwable t) {
                t.printStackTrace();
                Snackbar.make(mainLayout, "Jaringan Bermasalah!", Snackbar.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void updateAcceptanceStatus(Long idcaregiverTrxList, CaregiverOrder body){
        final Call<ResponseBody> resp = homecareTransactionAPIInterface.updatecaregiverTrxList(idcaregiverTrxList, body);

        resp.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    loadData();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Snackbar.make(mainLayout, "Gagal dalam proses perubahan data !", Snackbar.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }

    public void setData(ArrayList<CaregiverOrder> caregiverOrders){
        caregiverHistoryAdapter = new CaregiverHistoryAdapter(this, this, caregiverOrders);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        assignDateAndTime.addItemDecoration(dividerItemDecoration);
        assignDateAndTime.setLayoutManager(mLayoutManager);
        assignDateAndTime.setItemAnimator(new DefaultItemAnimator());
        assignDateAndTime.setAdapter(caregiverHistoryAdapter);
        assignDateAndTime.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), assignDateAndTime, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CaregiverOrder cgs = caregiverHistoryAdapter.getItem(position);
                if(cgs.getAcceptanceStatus()!=false || cgs.getAcceptanceStatus()==null){
                    displayDialog("Penugasan ke "+(position+1), cgs.getId());
                }
                else{
                    Snackbar.make(mainLayout, "Anda sudah pernah membatalkan penugasan ke "+(position+1), Snackbar.LENGTH_LONG).show();
                }
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillPage();
    }

    public void fillPage() {
        serviceName.setText(ConverterUtility.getDateString(homecareOrder.getDateTreatementStart()) +" s.d. "+ConverterUtility.getDateString(homecareOrder.getDateTreatementEnd()));
        loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
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

    private void setFonts() {
        ViewFaceUtility.applyFont(serviceName, this, "fonts/Dosis-Bold.otf");
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(caregiverHistoryTitle);
        arrayList.add(infoForReject);
        arrayList.add(infoForReject2);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }
}
