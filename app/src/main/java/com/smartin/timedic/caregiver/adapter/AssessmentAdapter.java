package com.smartin.timedic.caregiver.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smartin.timedic.caregiver.OrderDetailsActivity;
import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.model.Assessment;
import com.smartin.timedic.caregiver.model.OrderItem;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hafid on 27/04/2018.
 */

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.MyViewHolder>{

    public static final String TAG = "[ActiveOrdAdapter]";

    private List<Assessment> assessmentList;
    private Context context;
    private Activity activity;

    private boolean isLoadingAdded = false;

    public AssessmentAdapter(Activity activity, Context context, List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public AssessmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assessment_list, parent, false);
        return new AssessmentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentAdapter.MyViewHolder holder, int position) {
        final Assessment homecareOrder = assessmentList.get(position);
        holder.answer.setText(homecareOrder.getAnswer());
        holder.question.setText(homecareOrder.getQuestions());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.question)
        public TextView question;
        @BindView(R.id.answer)
        public TextView answer;

        public MyViewHolder(View view) {
            super(view);
            ArrayList<TextView> arrayList = new ArrayList<>();
            arrayList.add(question);
            arrayList.add(answer);
            ViewFaceUtility.applyFonts(arrayList, activity, "fonts/Dosis-Medium.otf");
        }
    }
}
