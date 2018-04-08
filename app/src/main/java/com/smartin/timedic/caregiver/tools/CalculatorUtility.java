package com.smartin.timedic.caregiver.tools;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Hafid on 1/11/2018.
 */

public class CalculatorUtility {
    public static final String TAG = "[CalculatorUtility]";

    /**
     * Pria : Berat badan ideal (kg) = [tinggi badan (cm) – 100] – [(tinggi badan (cm) – 100) x 10%]
     * Wanita : Berat badan ideal (kg) = [tinggi badan (cm) – 100] – [(tinggi badan (cm) – 100) x 15%]
     */
    public static float calculateBBI(float height, String gender) {
        float bbi = 0.f;
        if (gender.equals("Laki-laki")) {
            Log.i(TAG, "Laki-laki");
            bbi = (height - 100.f) - ((height - 100.f) * (float) (10.f / 100.f));
        } else {
            Log.i(TAG, "Perempuan");
            bbi = (height - 100.f) - ((height - 100.f) * (float) (15.f / 100.f));
        }
        return bbi;
    }

    /**
     * Pria = (66.5 + ( 13.75 × weight in kg ) + ( 5.003 × height in cm ) – ( 6.755 × age in years )) * alpha
     * Wanita  = (655.1 + ( 9.563 × weight in kg ) + ( 1.850 × height in cm ) – ( 4.676 × age in years )) * alpha
     *
     * @return
     */
    public static double calculateCaloriesNeed(double weight, double height, double age, String gender, double alpha) {
        double calories = 0;
        if (gender.equalsIgnoreCase("Laki-laki")) {
            calories = 66.5 + (13.75 * weight) + (5.003 * height) - (6.755 * age);
        } else {
            calories = 655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age);
        }

        return calories * alpha;
    }

    public static double calculatorFluidReq(double weight) {
        double fluidreq = 0;
        if (weight / 10 > 2) {
            fluidreq = 1500 + (weight - 20) * 20;
        } else if (weight / 10 == 2) {
            fluidreq = 1500;
        } else if (weight / 10 < 2) {
            fluidreq = 1000;
        }
        return fluidreq;
    }

    public static String calculatorIMT(double weight, double height) {
        double imt = 0;
        imt = weight / (height * height);
        if (imt < 18.5) {
            return "Berat badan kurang";
        } else if (imt < 22.9) {
            return "Normal";
        } else if (imt < 24.9) {
            return "Normal Tinggi";
        } else if (imt < 29.9) {
            return "Gemuk";
        } else {
            return "Sangat Gemuk";
        }

    }

    public static String calculatePregnancy(String lmp) throws ParseException {
        String prediction = "";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        final Calendar c = Calendar.getInstance();
        Date date = format.parse(lmp);
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        Log.i(TAG, month+"");
        int year = c.get(Calendar.YEAR);
        if (month >= 1 && month <= 3) {
            prediction = (day + 7) + "-" + (month + 9) + "-" + (year + 0);
        } else if (month >= 4 && month <= 12) {
            prediction = (day + 7) + "-" + (month - 3) + "-" + (year + 1);
        }
        Log.i(TAG, prediction);
        return prediction;
    }
}
