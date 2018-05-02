package com.smartin.timedic.caregiver.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.model.Religion;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

import java.util.List;

/**
 * Created by Hafid on 02/05/2018.
 */

public class ReligionAdapter extends BaseAdapter {
    Context context;
    Activity activity;
    List<Religion> religionList;
    LayoutInflater inflter;

    public ReligionAdapter(Context applicationContext, Activity activity, List<Religion> religionList) {
        this.context = applicationContext;
        this.religionList = religionList;
        this.activity = activity;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return religionList.size();
    }

    @Override
    public Object getItem(int i) {
        return religionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return religionList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Religion religion = religionList.get(i);
        view = inflter.inflate(R.layout.item_religion_adapter, null);
        TextView names = (TextView) view.findViewById(R.id.spinnerText);
        names.setText(religion.getReligion());
        ViewFaceUtility.applyFont(names, activity,"fonts/Dosis-Medium.otf");
        return view;
    }
}
