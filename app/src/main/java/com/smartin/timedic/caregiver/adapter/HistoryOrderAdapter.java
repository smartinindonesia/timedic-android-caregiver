package com.smartin.timedic.caregiver.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartin.timedic.caregiver.OrderDetailsActivity;
import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.model.HomecareOrder;
import com.smartin.timedic.caregiver.tools.ConverterUtility;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

/**
 * Created by Hafid on 11/25/2017.
 */

public class HistoryOrderAdapter extends RecyclerView.Adapter<HistoryOrderAdapter.MyViewHolder> {
    public static final String TAG = "[ActiveOrdAdapter]";

    private List<HomecareOrder> homecareOrderList;
    private Context context;
    private Activity activity;

    private boolean isLoadingAdded = false;

    public HistoryOrderAdapter(Activity activity, Context context, List<HomecareOrder> homecareOrders) {
        this.homecareOrderList = homecareOrders;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public HistoryOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_order, parent, false);
        return new HistoryOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HistoryOrderAdapter.MyViewHolder holder, int position) {
        final HomecareOrder homecareOrder = homecareOrderList.get(position);
        holder.serviceName.setText(homecareOrder.getSelectedService());
        holder.orderDescription.setText(homecareOrder.getTransactionDescription());
        holder.patientsName.setText(homecareOrder.getHomecarePatientId().getName());
        holder.transactionDate.setText(ConverterUtility.getDateStringCustomPattern(homecareOrder.getDate(), "dd-MM-yyyy HH:mm"));
        holder.transactionStatus.setText(homecareOrder.getHomecareTransactionStatus().getStatus());
        holder.gotoDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, OrderDetailsActivity.class);
                intent.putExtra("homecareOrder", homecareOrder);
                activity.startActivity(intent);
            }
        });
        holder.caregiverNum.setText(homecareOrder.getCaregiverArrayList().size()+" Perawat");
    }

    @Override
    public int getItemCount() {
        return homecareOrderList == null ? 0 : homecareOrderList.size();
    }

    public void add(HomecareOrder mc) {
        homecareOrderList.add(mc);
        notifyItemInserted(homecareOrderList.size() - 1);
    }

    public void addAll(List<HomecareOrder> mcList) {
        for (HomecareOrder mc : mcList) {
            add(mc);
        }
    }

    public void remove(HomecareOrder city) {
        int position = homecareOrderList.indexOf(city);
        if (position > -1) {
            homecareOrderList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        add(new HomecareOrder());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = homecareOrderList.size() - 1;
        HomecareOrder item = getItem(position);

        if (item != null) {
            homecareOrderList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public HomecareOrder getItem(int position) {
        return homecareOrderList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.serviceName)
        public TextView serviceName;
        @BindView(R.id.transactionDate)
        public TextView transactionDate;
        @BindView(R.id.orderDescripton)
        public TextView orderDescription;
        @BindView(R.id.patientsName)
        public TextView patientsName;
        @BindView(R.id.transactionStatus)
        public TextView transactionStatus;
        @BindView(R.id.orderDetails)
        public ImageButton gotoDetails;
        @BindView(R.id.caregiverNum)
        public TextView caregiverNum;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(serviceName, activity, "fonts/Dosis-Bold.otf");
            ArrayList<TextView> arrayList = new ArrayList<>();
            arrayList.add(transactionDate);
            arrayList.add(orderDescription);
            arrayList.add(patientsName);
            arrayList.add(transactionStatus);
            arrayList.add(caregiverNum);
            ViewFaceUtility.applyFonts(arrayList, activity, "fonts/Dosis-Medium.otf");
        }
    }
}
