package com.smartin.timedic.caregiver.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.ScheduleActivity;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.days;
import com.smartin.timedic.caregiver.model.parammodel.ScheduleParam;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.ScheduleInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleFragment extends Fragment {

    public static String TAG = "[ScheduleFragment]";
    public boolean statusData = false;

    private HomecareSessionManager homecareSessionManager;
    private ScheduleInterface scheduleInterfaceAPI;
    private SweetAlertDialog progressDialog;

    @BindView(R.id.scheduleLayout)
    LinearLayout scheduleLayout;

    @BindView(R.id.btnMonday)
    RelativeLayout btnMonday;

    @BindView(R.id.btnTuesday)
    RelativeLayout btnTuesday;

    @BindView(R.id.btnWednesday)
    RelativeLayout btnWednesday;

    @BindView(R.id.btnThursday)
    RelativeLayout btnThursday;

    @BindView(R.id.btnFriday)
    RelativeLayout btnFriday;

    @BindView(R.id.btnSaturday)
    RelativeLayout btnSaturday;

    @BindView(R.id.btnSunday)
    RelativeLayout btnSunday;

    //============

    @BindView(R.id.turnMonday)
    Switch turnMonday;

    @BindView(R.id.turnTuesday)
    Switch turnTuesday;

    @BindView(R.id.turnWednesday)
    Switch turnWednesday;

    @BindView(R.id.turnThursday)
    Switch turnThursday;

    @BindView(R.id.turnFriday)
    Switch turnFriday;

    @BindView(R.id.turnSaturday)
    Switch turnSaturday;

    @BindView(R.id.turnSunday)
    Switch turnSunday;






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homecareSessionManager = new HomecareSessionManager(getActivity(), getContext());
    }



    public void setFalseButton(){
        btnMonday.setClickable(false);
        btnTuesday.setClickable(false);
        btnWednesday.setClickable(false);
        btnThursday.setClickable(false);
        btnFriday.setClickable(false);
        btnSaturday.setClickable(false);
        btnSunday.setClickable(false);
    }

    private void openProgress(String title, String content) {
        progressDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
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


    public void cekData(Long id , String day, final ScheduleParam param){
        scheduleInterfaceAPI = APIClient.getClientWithToken(homecareSessionManager, getContext()).create(ScheduleInterface.class);
        Call<ResponseBody> cekScheduleData = scheduleInterfaceAPI.cekScheduleData(id, day);

        cekScheduleData.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 204) {
                    //statusData = false;
                    Log.d(TAG, "tidak ada data");
                    //addData(param);
                }
                else if(response.code() == 200){
                    //statusData = true;
                    Log.d(TAG, "ada data");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed Cek Data");
                call.cancel();
            }
        });
    }

    public void cekStatus(Long id){

        scheduleInterfaceAPI = APIClient.getClientWithToken(homecareSessionManager, getContext()).create(ScheduleInterface.class);
        Call<ResponseBody> cekScheduleData = scheduleInterfaceAPI.cekScheduleStatus(id);
        openProgress("Loading ...", "Sinkronisasi data server !");
        cekScheduleData.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 204) {
                }
                else if(response.code() == 200){

                    try {
                        String dataBody = response.body().string();
                        String[] parts = dataBody.split("],");
                        String[] pattern = new String[7];
                        String[] pattern2 = new String[7];
                        String[] Days = new String[7];
                        String[] value = new String[7];
                        String[] sts = new String[7];
                        days d = new days();
                        String idMonday = null, idTuesday = null, idWednesday = null, idThursday =  null, idFriday =  null, idSatuday = null, idSunday = null ;

                        for(int i=0;i<parts.length;i++) {
                            pattern[i] = parts[i].replace("[", "").replace("]]", "").replace('"', ' ').trim();
                            Days[i] = pattern[i].substring(0, pattern[i].indexOf(",")).trim();
                            pattern2[i] = pattern[i].substring(pattern[i].indexOf(",") + 1);
                            value[i] = pattern2[i].substring(0, pattern2[i].indexOf(",")).trim();
                            sts[i] = pattern2[i].substring(pattern2[i].indexOf(",")+1).trim();


                            if (Days[i].equals("monday")) {
                                d.setMonday(Boolean.parseBoolean(value[i]));
                                idMonday = sts[i];
                                turnMonday.setChecked(Boolean.parseBoolean(value[i]));
                            } else if (Days[i].equals("tuesday")) {
                                d.setTuesday(Boolean.parseBoolean(value[i]));
                                idTuesday = sts[i];
                                turnTuesday.setChecked(Boolean.parseBoolean(value[i]));
                            } else if (Days[i].equals("wednesday")) {
                                d.setWednesday(Boolean.parseBoolean(value[i]));
                                idWednesday = sts[i];
                                turnWednesday.setChecked(Boolean.parseBoolean(value[i]));
                            } else if (Days[i].equals("thursday")) {
                                d.setThursday(Boolean.parseBoolean(value[i]));
                                idThursday = sts[i];
                                turnThursday.setChecked(Boolean.parseBoolean(value[i]));
                            } else if (Days[i].equals("friday")) {
                                d.setFriday(Boolean.parseBoolean(value[i]));
                                idFriday = sts[i];
                                turnFriday.setChecked(Boolean.parseBoolean(value[i]));
                            } else if (Days[i].equals("saturday")) {
                                d.setSaturday(Boolean.parseBoolean(value[i]));
                                idSatuday = sts[i];
                                turnSaturday.setChecked(Boolean.parseBoolean(value[i]));
                            } else if (Days[i].equals("sunday")) {
                                d.setSunday(Boolean.parseBoolean(value[i]));
                                idSunday = sts[i];
                                turnSunday.setChecked(Boolean.parseBoolean(value[i]));
                            }
                        }
                         activateListener();
                         activateButton(idMonday, idTuesday, idWednesday, idThursday, idFriday, idSatuday, idSunday);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    closeProgress();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed Cek status");
                call.cancel();
                closeProgress();
            }

        });

    }

    public void activateButton(final String monday, final String tuesday, final String wednesday, final String thursday, final String friday, final String saturday, final String sunday){
        //final String idRow = id;

        btnMonday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                intent.putExtra("title","Senin");
                intent.putExtra("id",monday);
                startActivity(intent);
            }
        });

        btnTuesday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                intent.putExtra("title","Selasa");
                intent.putExtra("id",tuesday);
                startActivity(intent);
            }
        });

        btnWednesday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                intent.putExtra("title","Rabu");
                intent.putExtra("id",wednesday);
                startActivity(intent);
            }
        });

        btnThursday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                intent.putExtra("title","Kamis");
                intent.putExtra("id",thursday);
                startActivity(intent);
            }
        });

        btnFriday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                intent.putExtra("title","Jumat");
                intent.putExtra("id",friday);
                startActivity(intent);
            }
        });


        btnSaturday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                intent.putExtra("title","Sabtu");
                intent.putExtra("id",saturday);
                startActivity(intent);
            }
        });

        btnSunday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                intent.putExtra("title","Minggu");
                intent.putExtra("id",sunday);
                startActivity(intent);
            }
        });
    }

    public void updateSchedule(Long id, String day, boolean status, String message){
        Long idCaregiver = id;
        final String msg = message;
        scheduleInterfaceAPI = APIClient.getClientWithToken(homecareSessionManager, getContext()).create(ScheduleInterface.class);
        Call<ResponseBody> addSchedule = scheduleInterfaceAPI.updateScheduleStatusByIdCaregiverAndDay(idCaregiver, day, status);
        addSchedule.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 200) {
                    Snackbar.make(scheduleLayout, msg , Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(scheduleLayout, msg , Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "Failed");
                call.cancel();
            }
        });
    }


    public void activateListener(){

        final Long idCaregiver = homecareSessionManager.getUserDetail().getId();

        turnMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    updateSchedule(idCaregiver,"monday", true, "Jadwal Hari senin telah diaktifkan !");

                } else {
                    updateSchedule(idCaregiver,"monday", false,"Jadwal Hari senin telah dinonaktifkan !");
                }
            }
        });

        turnTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    updateSchedule(idCaregiver,"tuesday", true, "Jadwal Hari selasa telah diaktifkan !");

                } else {
                    updateSchedule(idCaregiver,"tuesday", false,"Jadwal Hari selasa telah dinonaktifkan !");
                }
            }
        });

        turnWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    updateSchedule(idCaregiver,"wednesday", true, "Jadwal Hari rabu telah diaktifkan !");

                } else {
                    updateSchedule(idCaregiver,"wednesday", false,"Jadwal Hari rabu telah dinonaktifkan !");
                }
            }
        });

        turnThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    updateSchedule(idCaregiver,"thursday", true, "Jadwal Hari kamis telah diaktifkan !");

                } else {
                    updateSchedule(idCaregiver,"thursday", false,"Jadwal Hari kamis telah dinonaktifkan !");
                }
            }
        });

        turnFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    updateSchedule(idCaregiver,"friday", true, "Jadwal Hari jumat telah diaktifkan !");

                } else {
                    updateSchedule(idCaregiver,"friday", false,"Jadwal Hari jumat telah dinonaktifkan !");
                }
            }
        });

        turnSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    updateSchedule(idCaregiver,"saturday", true, "Jadwal Hari sabtu telah diaktifkan !");

                } else {
                    updateSchedule(idCaregiver,"saturday", false,"Jadwal Hari sabtu telah dinonaktifkan !");
                }
            }
        });

        turnSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    updateSchedule(idCaregiver,"sunday", true, "Jadwal Hari minggu telah diaktifkan !");

                } else {
                    updateSchedule(idCaregiver,"sunday", false,"Jadwal Hari minggu telah dinonaktifkan !");
                }
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && isResumed()) { // fragment is visible and have created
            Long idCaregiver = homecareSessionManager.getUserDetail().getId();
            cekStatus(idCaregiver);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View newView = inflater.inflate(R.layout.fragment_schedule, container, false);

        ButterKnife.bind(this, newView);
        Long idCaregiver = homecareSessionManager.getUserDetail().getId();

        if(getUserVisibleHint()){ // fragment is visible
            cekStatus(idCaregiver);
        }

        return newView;
    }
}
