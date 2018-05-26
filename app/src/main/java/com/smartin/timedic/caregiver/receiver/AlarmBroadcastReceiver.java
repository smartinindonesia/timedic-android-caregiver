package com.smartin.timedic.caregiver.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.smartin.timedic.caregiver.AlarmActivity;
import com.smartin.timedic.caregiver.model.utilitymodel.AlarmModel;
import com.smartin.timedic.caregiver.service.AlarmNotificationService;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "[AlarmBroadcastRcvr]";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle b = intent.getExtras();
        String medicineName = b.getString(AlarmModel.ACTION_MEDICINE_NAME);
        Intent a = new Intent(context, AlarmActivity.class).putExtras(b);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(a);

        //This will send a notification message and show notification in notification tray

        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmNotificationService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
    }
}
