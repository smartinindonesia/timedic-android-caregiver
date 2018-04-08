package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthCalculatorActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnBBI)
    Button btnBbi;
    @BindView(R.id.btnTaksirHamil)
    Button btnPregnantCalc;
    @BindView(R.id.btnKebCairan)
    Button btnFluidReq;
    @BindView(R.id.btnIMT)
    Button btnImt;
    @BindView(R.id.btnKebKalori)
    Button btnCaloryNeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_calculator);
        ButterKnife.bind(this);
        createTitleBar();
        btnBbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthCalculatorActivity.this, CalculatorBBI.class);
                startActivity(intent);
            }
        });
        btnPregnantCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthCalculatorActivity.this, CalculatorPregnancyEst.class);
                startActivity(intent);
            }
        });
        btnFluidReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthCalculatorActivity.this, CalculatorFluidReq.class);
                startActivity(intent);
            }
        });
        btnCaloryNeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthCalculatorActivity.this, CalculatorCaloricNeeds.class);
                startActivity(intent);
            }
        });
        btnImt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HealthCalculatorActivity.this, CalculatorIMT.class);
                startActivity(intent);
            }
        });
        setFontView();
    }

    private void setFontView(){
        ViewFaceUtility.applyFont(btnBbi, this, "fonts/Dosis-Regular.otf");
        ViewFaceUtility.applyFont(btnPregnantCalc, this, "fonts/Dosis-Regular.otf");
        ViewFaceUtility.applyFont(btnCaloryNeed, this, "fonts/Dosis-Regular.otf");
        ViewFaceUtility.applyFont(btnFluidReq, this, "fonts/Dosis-Regular.otf");
        ViewFaceUtility.applyFont(btnImt, this, "fonts/Dosis-Regular.otf");
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
}
