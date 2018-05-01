package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.smartin.timedic.caregiver.adapter.TimeSpinnerAdapter;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.parammodel.ScheduleParam;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.ScheduleInterface;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {

    public static final String TAG = "[ScheduleActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;

    @BindView(R.id.startTime)
    Spinner startTime;

    @BindView(R.id.endTime)
    Spinner endTime;

    @BindView(R.id.startTime2)
    Spinner startTime2;

    @BindView(R.id.endTime2)
    Spinner endTime2;

    @BindView(R.id.btnSetSchedule)
    Button btnSetSchedule;

    @BindView(R.id.firstSessionTitle)
    TextView firstSessionTitle;
    @BindView(R.id.firstSessionStartTitle)
    TextView firstSessionStartTitle;
    @BindView(R.id.firstSessionEndTitle)
    TextView firstSessionEndTitle;
    @BindView(R.id.secondSessionTitle)
    TextView secondSessionTitle;
    @BindView(R.id.secondSessionStartTitle)
    TextView secondSessionStartTitle;
    @BindView(R.id.secondSessionEndTitle)
    TextView secondSessionEndTitle;

    private SweetAlertDialog progressDialog;

    TimeSpinnerAdapter adapterStartTime, adapterEndTime, adapterStartTime2, adapterEndTime2;

    private HomecareSessionManager homecareSessionManager;
    private ScheduleInterface scheduleInterfaceAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        final Intent intent = getIntent();

        createTitleBar(intent);

        homecareSessionManager = new HomecareSessionManager(this, getApplicationContext());

        List<String> time = Arrays.asList(getResources().getStringArray(R.array.time));

        adapterStartTime = new TimeSpinnerAdapter(this, this, time);
        startTime.setAdapter(adapterStartTime);

        adapterEndTime = new TimeSpinnerAdapter(this, this, time);
        endTime.setAdapter(adapterEndTime);

        adapterStartTime2 = new TimeSpinnerAdapter(this, this, time);
        startTime2.setAdapter(adapterStartTime2);

        adapterEndTime2 = new TimeSpinnerAdapter(this, this, time);
        endTime2.setAdapter(adapterEndTime2);


        long id = Long.parseLong(intent.getStringExtra("id"));


        getData(id);

        btnSetSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = Long.parseLong(intent.getStringExtra("id"));

                String time1 = startTime.getSelectedItem().toString();
                String time2 = startTime2.getSelectedItem().toString();
                String end1 = endTime.getSelectedItem().toString();
                String end2 = endTime2.getSelectedItem().toString();

                updateData(id, time1, time2, end1, end2);
            }
        });
        setFonts();
    }

    public int cekPosition(String time) {

        int pos = 0;

        //Log.d("Masuk ", time);

        if (time.equals("00:00:00")) {
            pos = 0;
        } else if (time.equals("01:00:00")) {
            pos = 1;
        } else if (time.equals("02:00:00")) {
            pos = 2;
        } else if (time.equals("03:00:00")) {
            pos = 3;
        } else if (time.equals("04:00:00")) {
            pos = 4;
        } else if (time.equals("05:00:00")) {
            pos = 5;
        } else if (time.equals("06:00:00")) {
            pos = 6;
        } else if (time.equals("07:00:00")) {
            pos = 7;
        } else if (time.equals("08:00:00")) {
            pos = 8;
        } else if (time.equals("09:00:00")) {
            pos = 9;
        } else if (time.equals("10:00:00")) {
            pos = 10;
        } else if (time.equals("11:00:00")) {
            pos = 11;
        } else if (time.equals("12:00:00")) {
            pos = 12;
        } else if (time.equals("13:00:00")) {
            pos = 13;
        } else if (time.equals("14:00:00")) {
            pos = 14;
        } else if (time.equals("15:00:00")) {
            pos = 15;
        } else if (time.equals("16:00:00")) {
            pos = 16;
        } else if (time.equals("17:00:00")) {
            pos = 17;
        } else if (time.equals("18:00:00")) {
            pos = 18;
        } else if (time.equals("19:00:00")) {
            pos = 19;
        } else if (time.equals("20:00:00")) {
            pos = 20;
        } else if (time.equals("21:00:00")) {
            pos = 21;
        } else if (time.equals("22:00:00")) {
            pos = 22;
        } else if (time.equals("23:00:00")) {
            pos = 23;
        }

        return pos;
    }

    public void getData(Long id) {
        scheduleInterfaceAPI = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(ScheduleInterface.class);
        Call<ResponseBody> cekScheduleData = scheduleInterfaceAPI.getScheduleById(id);
        openProgress("Loading ...", "Sinkronisasi data server !");
        cekScheduleData.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        JsonParser parser = new JsonParser();
                        JsonObject json = (JsonObject) parser.parse(response.body().string());
                        //Log.d("Start Time 1 ", json.get("startTime").toString());
                        //Log.d("Position Start Time 1 ", String.valueOf(cekPosition(json.get("startTime").toString().replace('"',' ').trim())));

                        startTime.setSelection(cekPosition(json.get("startTime").toString().replace('"', ' ').trim()));
                        startTime2.setSelection(cekPosition(json.get("startTime2").toString().replace('"', ' ').trim()));
                        endTime.setSelection(cekPosition(json.get("endTime").toString().replace('"', ' ').trim()));
                        endTime2.setSelection(cekPosition(json.get("endTime2").toString().replace('"', ' ').trim()));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    closeProgress();
                } else {
                    Toast.makeText(getApplicationContext(), "gagal sinkronisasi data !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed Cek Data");
                call.cancel();
            }
        });

    }

    public void updateData(Long id, String time1, String time2, String end1, String end2) {
        scheduleInterfaceAPI = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(ScheduleInterface.class);
        Call<ResponseBody> cekScheduleData = scheduleInterfaceAPI.updateScheduleForTime(id, time1, time2, end1, end2);

        cekScheduleData.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(getApplicationContext(), "Jadwal telah berhasil diubah", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Jadwal gagal diubah", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed Cek Data");
                call.cancel();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    private void openProgress(String title, String content) {
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.dismiss();
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(title);
        progressDialog.setContentText(content);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.show();
    }

    private void closeProgress() {
        progressDialog.dismiss();
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar(Intent x) {
        String title_name = x.getStringExtra("title");
        setSupportActionBar(toolbar);
        this.setTitle("Atur Jadwal Hari " + title_name);
        ViewFaceUtility.changeToolbarFont(toolbar, this, "fonts/BalooBhaina-Regular.ttf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    public void setFonts() {
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(firstSessionEndTitle);
        arrayList.add(firstSessionStartTitle);
        arrayList.add(secondSessionEndTitle);
        arrayList.add(secondSessionStartTitle);
        arrayList.add(btnSetSchedule);
        ArrayList<TextView> arrayList1 = new ArrayList<>();
        arrayList.add(firstSessionTitle);
        arrayList.add(secondSessionTitle);
        ViewFaceUtility.applyFonts(arrayList1, this, "fonts/Dosis-Bold.otf");
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
