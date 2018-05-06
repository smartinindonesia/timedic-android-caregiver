package com.smartin.timedic.caregiver.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.model.CaregiverOrder;
import com.smartin.timedic.caregiver.tools.ConverterUtility;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

/**
 * Created by Hafid on 3/27/2018.
 */

public class CaregiverHistoryAdapter extends RecyclerView.Adapter<CaregiverHistoryAdapter.MyViewHolder> {

    private List<CaregiverOrder> caregiverOrders;
    private Context context;
    private Activity activity;

    public CaregiverHistoryAdapter(Activity activity, Context context, List<CaregiverOrder> caregiverOrders) {
        this.caregiverOrders = caregiverOrders;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_caregiver_history, parent, false);
        return new CaregiverHistoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CaregiverOrder caregiverOrder = caregiverOrders.get(position);
        holder.caregiverName.setText("Penugasan Ke "+(position+1)+" : "+ConverterUtility.getDateString(caregiverOrder.getDate()) + ", " +caregiverOrder.getTime());
        if(caregiverOrder != null){
            if(!caregiverOrder.getAcceptanceStatus()){
                holder.caregiverName.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public int getItemCount() {
        return caregiverOrders.size();
    }

    public CaregiverOrder getItem(int position) {
        return caregiverOrders.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.caregiverName)
        public TextView caregiverName;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(caregiverName, activity, "fonts/Dosis-Medium.otf");
        }
    }
}
