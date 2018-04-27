package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;

import com.smartin.timedic.caregiver.adapter.AssessmentAdapter;
import com.smartin.timedic.caregiver.model.Assessment;
import com.smartin.timedic.caregiver.model.HomecareOrder;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssessmentDetailActivity extends AppCompatActivity {
    public static final String TAG = "[AssessmentDetailAct]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.assessmentList)
    RecyclerView recyclerView;

    HomecareOrder homecareOrder;
    AssessmentAdapter assessmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        ButterKnife.bind(this);
        createTitleBar();
        homecareOrder = (HomecareOrder) getIntent().getSerializableExtra("order_details");
        Log.i(TAG, homecareOrder.getLocationLongitude()+" Latitude");
        assessmentAdapter = new AssessmentAdapter(this, this, homecareOrder.getHomecareAssessmentRecordList());
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        //recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(assessmentAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        finish();
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
}
