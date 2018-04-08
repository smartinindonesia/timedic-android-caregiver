package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    ArrayAdapter<CharSequence> adapterStartTime, adapterEndTime, adapterStartTime2, adapterEndTime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        createTitleBar();

        adapterStartTime = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapterStartTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startTime.setAdapter(adapterStartTime);

        adapterEndTime = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapterEndTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endTime.setAdapter(adapterEndTime);

        adapterStartTime2 = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapterStartTime2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startTime2.setAdapter(adapterStartTime2);

        adapterEndTime2 = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapterEndTime2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endTime2.setAdapter(adapterEndTime2);

        btnSetSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Masih gue kerjain bro !", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
