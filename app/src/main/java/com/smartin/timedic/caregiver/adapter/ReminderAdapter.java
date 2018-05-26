package com.smartin.timedic.caregiver.adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smartin.timedic.caregiver.AddReminderItemActivity;
import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.config.Action;
import com.smartin.timedic.caregiver.model.utilitymodel.AlarmModel;
import com.smartin.timedic.caregiver.receiver.AlarmBroadcastReceiver;
import com.smartin.timedic.caregiver.tools.ConverterUtility;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.sqlitehelper.DBHelperAlarmModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Hafid on 8/23/2017.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder> {
    public static String TAG = "[ReminderAdapter]";

    private List<AlarmModel> reminderList;
    private Activity activity;
    private Context context;
    private AlarmManager mgrAlarm;
    private ReminderAdapter adapter;

    public ReminderAdapter(Context context, Activity activity, List<AlarmModel> reminderList) {
        this.reminderList = reminderList;
        this.activity = activity;
        this.context = context;
        this.adapter = this;
        this.mgrAlarm = (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicine_reminder_list, parent, false);
        return new ReminderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final AlarmModel remind = reminderList.get(position);
        final DBHelperAlarmModel dbHelperAlarmModel = new DBHelperAlarmModel(context, remind);
        holder.medicineName.setText(remind.getMedicineName());
        holder.dose.setText(remind.getNumOfMedicine() + "x" + remind.getIntervalTime() + " " + remind.getMedicineShape() + "/" + remind.getIntervalDay() + " hari");
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MMM-yyyy";
        String dateSet = ConverterUtility.convertDate(remind.getStartingDate(), inputPattern, outputPattern);
        holder.treatmentDate.setText("Dimulai tanggal " + dateSet);
        String status = "";
        if (remind.getStatus().getId() == 1) {
            holder.status.setText("Alarm aktif");
        } else {
            holder.status.setText("Alarm mati");
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, AddReminderItemActivity.class);
                intent.putExtra("alarm_object", remind);
                intent.putExtra("iseditmode", true);
                activity.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SweetAlertDialog sdialog = new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE);
                sdialog.setTitleText("Apakah anda yakin?");
                sdialog.setContentText("Data tidak dapat dikembalikan setelah terhapus!");
                sdialog.setConfirmText("Ya");
                sdialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        int result = dbHelperAlarmModel.deleteAlarm();
                        for (int i = 0; i < remind.getTime().size(); i++) {
                            try {
                                long timeAdded = addTime(remind.getStartingDate(), remind.getTime().get(i).getTime());
                                PendingIntent pi = createAlarmPendingIntent(remind.getTime().get(i).getId(), timeAdded, remind.getIntervalDay(), remind);
                                cancelAlarmPendingIntent(pi);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        if (result > 0) {
                            resetData();
                        }
                    }
                });
                sdialog.setCancelable(true);
                sdialog.setCancelText("Tidak");
                sdialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public void resetData() {
        DBHelperAlarmModel dbHelperAlarmModel = new DBHelperAlarmModel(context, new AlarmModel());
        reminderList.clear();
        List<AlarmModel> temp = dbHelperAlarmModel.getAllAlarm();
        for (int i = 0; i < temp.size(); i++) {
            reminderList.add(temp.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.medicineName)
        public TextView medicineName;
        @BindView(R.id.dose)
        public TextView dose;
        @BindView(R.id.startingDate)
        public TextView treatmentDate;
        @BindView(R.id.status)
        public TextView status;
        @BindView(R.id.btnEdit)
        public Button edit;
        @BindView(R.id.btnDelete)
        public Button delete;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(medicineName, activity, "fonts/Dosis-Bold.otf");
            ArrayList<TextView> arrayList = new ArrayList<>();
            arrayList.add(dose);
            arrayList.add(treatmentDate);
            arrayList.add(status);
            arrayList.add(edit);
            arrayList.add(delete);
            ViewFaceUtility.applyFonts(arrayList, activity, "fonts/Dosis-Medium.otf");
        }

    }

    private PendingIntent createAlarmPendingIntent(long index, long date, int dayInterval, AlarmModel alarmModel) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(Action.ACTION_ALARM_OBJECT_TRANSFER, alarmModel);
        // Loop counter `i` is used as a `requestCode`
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int) index, intent, 0);
        // Single alarms in 1, 2, ..., 10 minutes (in `i` minutes)
        long intervalRepeater = 24 * 60 * 60 * 1000 * dayInterval;
        mgrAlarm.set(AlarmManager.RTC_WAKEUP,
                date,
                pendingIntent);
        return pendingIntent;
    }

    private void cancelAlarmPendingIntent(PendingIntent intent) {
        mgrAlarm.cancel(intent);
    }

    private long addTime(String inDate, String inTime) throws ParseException {
        String inputPatternDate = "yyyy-MM-dd HH:mm:ss";
        DateFormat formatterDate = new SimpleDateFormat(inputPatternDate);
        String[] date = inDate.split(" ");
        String dateNewInStr = date[0] + " " + inTime;
        Date dateNew = (Date) formatterDate.parse(dateNewInStr);

        Log.i(TAG, dateNew.getTime() + " " + dateNewInStr);
        return dateNew.getTime();
    }
}

