package com.smartin.timedic.caregiver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartin.timedic.caregiver.model.AlphaCalcActivity;
import java.util.List;
import com.smartin.timedic.caregiver.R;

/**
 * Created by Hafid on 2/15/2018.
 */

public class AlphaCalcSpinnerAdapter extends BaseAdapter {
    Context context;
    List<AlphaCalcActivity> alphaCalcActivities;
    LayoutInflater inflter;

    public AlphaCalcSpinnerAdapter(Context applicationContext, List<AlphaCalcActivity> alphaCalcActivities) {
        this.context = applicationContext;
        this.alphaCalcActivities = alphaCalcActivities;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return alphaCalcActivities.size();
    }

    @Override
    public Object getItem(int i) {
        return alphaCalcActivities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alphaCalcActivities.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AlphaCalcActivity activity = alphaCalcActivities.get(i);
        view = inflter.inflate(R.layout.item_spinner_icon_red, null);
        ImageView icon = (ImageView) view.findViewById(R.id.spinnerIcon);
        TextView names = (TextView) view.findViewById(R.id.spinnerText);
        icon.setImageResource(activity.getResourceId());
        names.setText(activity.getActname());
        return view;
    }
}