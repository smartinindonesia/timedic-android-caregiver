package com.smartin.timedic.caregiver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.smartin.timedic.caregiver.model.GenderOption;
import java.util.List;
import com.smartin.timedic.caregiver.R;

/**
 * Created by Hafid on 2/5/2018.
 */

public class GenderSpinnerAdapter extends BaseAdapter {
    Context context;
    List<GenderOption> genderOptionList;
    LayoutInflater inflter;

    public GenderSpinnerAdapter(Context applicationContext, List<GenderOption> genderOptions) {
        this.context = applicationContext;
        this.genderOptionList = genderOptions;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return genderOptionList.size();
    }

    @Override
    public Object getItem(int i) {
        return genderOptionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return genderOptionList.get(i).getIconId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GenderOption genderOption = genderOptionList.get(i);
        view = inflter.inflate(R.layout.item_spinner_icon_blue, null);
        ImageView icon = (ImageView) view.findViewById(R.id.spinnerIcon);
        TextView names = (TextView) view.findViewById(R.id.spinnerText);
        icon.setImageResource(genderOption.getIconId());
        names.setText(genderOption.getGender());
        return view;
    }
}