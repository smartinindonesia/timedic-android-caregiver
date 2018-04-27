package com.smartin.timedic.caregiver.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.smartin.timedic.caregiver.OrderDetailsActivity;
import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.model.HomecareOrder;
import com.smartin.timedic.caregiver.model.OrderItem;
import com.smartin.timedic.caregiver.tools.ConverterUtility;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

/**
 * Created by Hafid on 11/25/2017.
 */

public class ActiveOrderAdapter extends RecyclerView.Adapter<ActiveOrderAdapter.MyViewHolder> {
    public static final String TAG = "[ActiveOrdAdapter]";

    private List<OrderItem> homecareOrderList;
    private Context context;
    private Activity activity;

    private boolean isLoadingAdded = false;

    public ActiveOrderAdapter(Activity activity, Context context, List<OrderItem> homecareOrders) {
        this.homecareOrderList = homecareOrders;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public ActiveOrderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active_order, parent, false);
        return new ActiveOrderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActiveOrderAdapter.MyViewHolder holder, int position) {
        final OrderItem homecareOrder = homecareOrderList.get(position);
        holder.caregiverName.setText(homecareOrder.getCaregiverName());
        String strTime, strDay;
        strDay = "";
        strTime = "";
        if (homecareOrder.getTime() != null) {
            strTime = homecareOrder.getTime();
        }
        if (homecareOrder.getDay() != null) {
            strDay = homecareOrder.getDay();
        }
        holder.time.setText("Waktu : " + strTime);
        holder.day.setText("Hari : " + strDay);
        holder.orderDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, OrderDetailsActivity.class);
                intent.putExtra("homecareOrder", homecareOrder);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return homecareOrderList == null ? 0 : homecareOrderList.size();
    }

    public void add(OrderItem mc) {
        homecareOrderList.add(mc);
        notifyItemInserted(homecareOrderList.size() - 1);
    }

    public void addAll(List<OrderItem> mcList) {
        for (OrderItem mc : mcList) {
            add(mc);
        }
    }

    public void remove(OrderItem city) {
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
        add(new OrderItem());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = homecareOrderList.size() - 1;
        OrderItem item = getItem(position);

        if (item != null) {
            homecareOrderList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public OrderItem getItem(int position) {
        return homecareOrderList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.caregiverName)
        public TextView caregiverName;
        @BindView(R.id.day)
        public TextView day;
        @BindView(R.id.time)
        public TextView time;
        @BindView(R.id.orderDetails)
        public Button orderDetails;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(caregiverName, activity, "fonts/Dosis-Bold.otf");
            ArrayList<TextView> arrayList = new ArrayList<>();
            arrayList.add(day);
            arrayList.add(time);
            arrayList.add(orderDetails);
            ViewFaceUtility.applyFonts(arrayList, activity, "fonts/Dosis-Medium.otf");
        }
    }
}
