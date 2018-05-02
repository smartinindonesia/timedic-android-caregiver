package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smartin.timedic.caregiver.tools.CalculatorUtility;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class CalculatorPregnancyEst extends AppCompatActivity {
    public static final String TAG = "[CalculatorPregnancyEst]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.btnCalculate)
    LinearLayout btnCalculate;
    @BindView(R.id.btnChooseDate)
    ImageButton btnChooseDate;
    @BindView(R.id.selectedDateTex)
    EditText dateTex;

    @BindView(R.id.btnCalculateTitle)
    TextView btnCalculateTitle;
    @BindView(R.id.descriptionTitle)
    TextView descriptionTitle;
    @BindView(R.id.dateTitle)
    TextView dateTitle;

    private DatePickerDialog datePickerDialog;
    private SweetAlertDialog sweetAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_pregnancy_est);
        ButterKnife.bind(this);
        createTitleBar();
        btnChooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH) + 2;
                int month = mcurrentTime.get(Calendar.MONTH);
                int year = mcurrentTime.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(CalculatorPregnancyEst.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateTex.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mcurrentTime.get(Calendar.YEAR), mcurrentTime.get(Calendar.MONTH), mcurrentTime.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setTitle("Pilih tanggal pelayanan");
                datePickerDialog.show();
                dateTex.setText(day + "-" + (month + 1) + "-" + year);
            }
        });
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePregnancy();
            }
        });
        setFonts();
    }

    private void calculatePregnancy() {
        if (validateForm()) {
            try {
                String date = CalculatorUtility.calculatePregnancy(dateTex.getText().toString());
                sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
                sweetAlertDialog.setTitleText("Hasil").setContentText("Taksiran kehamilan anda pada tanggal " + date);
                sweetAlertDialog.show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            Snackbar.make(mainLayout, "Mohon lengkapi form terlebih dahulu!", Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean validateForm() {
        return !dateTex.getText().toString().equals("");
    }

    private void resetForm() {
        dateTex.setText("");
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

    private void setFonts(){
        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(btnCalculateTitle);
        arrayList.add(descriptionTitle);
        arrayList.add(dateTitle);
        arrayList.add(btnCalculateTitle);
        arrayList.add(dateTex);
        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }
}
