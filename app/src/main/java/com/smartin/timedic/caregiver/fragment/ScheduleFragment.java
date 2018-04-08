package com.smartin.timedic.caregiver.fragment;

import android.content.Context;
import android.content.Intent;
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

import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.ScheduleActivity;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
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

    public void cekData(Long id , String day, final ScheduleParam param){
        scheduleInterfaceAPI = APIClient.getClientWithToken(homecareSessionManager, getContext()).create(ScheduleInterface.class);
        Call<ResponseBody> cekScheduleData = scheduleInterfaceAPI.cekScheduleData(id, day);

        cekScheduleData.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 204) {
                    statusData = false;
                    Log.d(TAG, "tidak ada data");
                    addData(param);
                }
                else if(response.code() == 200){
                    statusData = true;
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

    public void addData(ScheduleParam param){
        scheduleInterfaceAPI = APIClient.getClientWithToken(homecareSessionManager, getContext()).create(ScheduleInterface.class);
        Call<ResponseBody> addSchedule = scheduleInterfaceAPI.addSchedule(param);
        addSchedule.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 201) {
                    Toast.makeText(getContext(), "Jadwal Hari senin telah ditambahkan", Toast.LENGTH_LONG).show();
                    //gotoLogin();
                } else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        //Snackbar.make(mainLayout, object.getString("message"), Snackbar.LENGTH_LONG).show();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, newView);
        homecareSessionManager = new HomecareSessionManager(getActivity(), getContext());


        turnMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Snackbar.make(scheduleLayout, "Jadwal Hari senin telah diaktifkan, silahkan isi jadwal untuk jam nya !", Snackbar.LENGTH_LONG).show();

                    ScheduleParam param = new ScheduleParam();
                    String day = "monday";
                    Long idCaregiver = homecareSessionManager.getUserDetail().getId();
                    param.setDay(day);
                    param.setStartTime("");
                    param.setEndTime("");
                    param.setStartTime2("");
                    param.setEndTime2("");
                    param.setIdHomecareCaregiver(idCaregiver);
                    param.setStatus(true);

                    cekData(idCaregiver, day, param);

                } else {
                    Snackbar.make(scheduleLayout, "Jadwal Hari senin telah dinonaktifkan !", Snackbar.LENGTH_LONG).show();
                    //btnMonday.setClickable(false);
                }
            }
        });

        turnTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //appSetting.setActive(b);
                //homecareSessionManager.setSetting(appSetting);
                if (b) {
                    Snackbar.make(scheduleLayout, "Jadwal Hari selasa telah diaktifkan, silahkan isi jadwal untuk jam nya !", Snackbar.LENGTH_LONG).show();
                    btnTuesday.setClickable(true);
                } else {
                    Snackbar.make(scheduleLayout, "Jadwal Hari selasa telah dinonaktifkan !", Snackbar.LENGTH_LONG).show();
                    btnTuesday.setClickable(false);
                }
            }
        });

        turnWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //appSetting.setActive(b);
                //homecareSessionManager.setSetting(appSetting);
                if (b) {
                    Snackbar.make(scheduleLayout, "Jadwal Hari rabu telah diaktifkan, silahkan isi jadwal untuk jam nya !", Snackbar.LENGTH_LONG).show();
                    btnWednesday.setClickable(true);
                } else {
                    Snackbar.make(scheduleLayout, "Jadwal Hari rabu telah dinonaktifkan !", Snackbar.LENGTH_LONG).show();
                    btnWednesday.setClickable(false);
                }
            }
        });

        turnThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //appSetting.setActive(b);
                //homecareSessionManager.setSetting(appSetting);
                if (b) {
                    Snackbar.make(scheduleLayout, "Jadwal Hari kamis telah diaktifkan, silahkan isi jadwal untuk jam nya !", Snackbar.LENGTH_LONG).show();
                    btnThursday.setClickable(true);
                } else {
                    Snackbar.make(scheduleLayout, "Jadwal Hari kamis telah dinonaktifkan !", Snackbar.LENGTH_LONG).show();
                    btnThursday.setClickable(false);
                }
            }
        });

        turnFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //appSetting.setActive(b);
                //homecareSessionManager.setSetting(appSetting);
                if (b) {
                    Snackbar.make(scheduleLayout, "Jadwal Hari jumat telah diaktifkan, silahkan isi jadwal untuk jam nya !", Snackbar.LENGTH_LONG).show();
                    btnFriday.setClickable(true);
                } else {
                    Snackbar.make(scheduleLayout, "Jadwal Hari jumat telah dinonaktifkan !", Snackbar.LENGTH_LONG).show();
                    btnFriday.setClickable(false);
                }
            }
        });

        turnSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //appSetting.setActive(b);
                //homecareSessionManager.setSetting(appSetting);
                if (b) {
                    Snackbar.make(scheduleLayout, "Jadwal Hari sabtu telah diaktifkan, silahkan isi jadwal untuk jam nya !", Snackbar.LENGTH_LONG).show();
                    btnSaturday.setClickable(true);
                } else {
                    Snackbar.make(scheduleLayout, "Jadwal Hari sabtu telah dinonaktifkan !", Snackbar.LENGTH_LONG).show();
                    btnSaturday.setClickable(false);
                }
            }
        });

        turnSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //appSetting.setActive(b);
                //homecareSessionManager.setSetting(appSetting);
                if (b) {
                    Snackbar.make(scheduleLayout, "Jadwal Hari minggu telah diaktifkan, silahkan isi jadwal untuk jam nya !", Snackbar.LENGTH_LONG).show();
                    btnSunday.setClickable(true);
                } else {
                    Snackbar.make(scheduleLayout, "Jadwal Hari minggu telah dinonaktifkan !", Snackbar.LENGTH_LONG).show();
                    btnSunday.setClickable(false);
                }
            }
        });


        btnMonday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Snackbar.make(accountLayout, "Bisa diklik !", Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(intent);
            }
        });


        btnTuesday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(scheduleLayout, "Bisa diklik !", Snackbar.LENGTH_LONG).show();
            }
        });

        btnWednesday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(scheduleLayout, "Bisa diklik !", Snackbar.LENGTH_LONG).show();
            }
        });

        btnThursday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(scheduleLayout, "Bisa diklik !", Snackbar.LENGTH_LONG).show();
            }
        });

        btnFriday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(scheduleLayout, "Bisa diklik !", Snackbar.LENGTH_LONG).show();
            }
        });

        btnSaturday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(scheduleLayout, "Bisa diklik !", Snackbar.LENGTH_LONG).show();
            }
        });

        btnSunday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Snackbar.make(scheduleLayout, "Bisa diklik !", Snackbar.LENGTH_LONG).show();
            }
        });



        return newView;
    }
}
