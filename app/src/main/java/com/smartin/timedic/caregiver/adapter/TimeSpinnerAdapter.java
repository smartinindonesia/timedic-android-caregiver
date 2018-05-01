package com.smartin.timedic.caregiver.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

import java.util.List;

/**
 * Created by Hafid on 01/05/2018.
 */

public class TimeSpinnerAdapter extends BaseAdapter {
    Context context;
    Activity activity;
    List<String> timeList;
    LayoutInflater inflter;

    public TimeSpinnerAdapter(Context applicationContext, Activity activity, List<String> timeList) {
        this.context = applicationContext;
        this.timeList = timeList;
        this.activity = activity;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return timeList.size();
    }

    @Override
    public Object getItem(int i) {
        return timeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String genderOption = timeList.get(i);
        view = inflter.inflate(R.layout.item_time_adapter, null);
        TextView names = (TextView) view.findViewById(R.id.time);
        names.setText(genderOption);
        ViewFaceUtility.applyFont(names, activity, "fonts/Dosis-Medium.otf");
        return view;
    }
}