package com.smartin.timedic.caregiver.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.model.utilitymodel.AlarmModel;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AlarmNotificationService extends IntentService {
    public static final String TAG = "[AlarmNotificationSvc]";
    private NotificationManager alarmNotificationManager;

    //Notification ID for Alarm
    public static final int NOTIFICATION_ID = 1;

    public AlarmNotificationService() {
        super("AlarmNotificationService");
    }

    @Override
    public void onHandleIntent(Intent intent) {

        //Send notification

        updateUI(intent.getExtras());
    }

    private void updateUI(Bundle extras){
        if(extras != null) {
            Log.i(TAG, "NOT NULL extras");
            if (extras.containsKey(AlarmModel.ACTION_MEDICINE_NAME)) {
                String medicineName = extras.getString(AlarmModel.ACTION_MEDICINE_NAME);
                Integer numOfMedicine = extras.getInt(AlarmModel.ACTION_NUM_OF_MEDICINE);
                Integer intervalTime = extras.getInt(AlarmModel.ACTION_INTERVAL_TIME);
                String medicineShape = extras.getString(AlarmModel.ACTION_MEDICINE_SHAPE);
                Integer intervalDay = extras.getInt(AlarmModel.ACTION_INTERVAL_DAY);
                String notifText = (medicineName + " " + numOfMedicine + "x" + intervalTime + " " + medicineShape + "/" + intervalDay + " hari");
                alarmNotificationManager = (NotificationManager) this
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                //Create notification
                NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                        this).setContentTitle("Alarm - Saatnya minum obat!").setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notifText))
                        .setContentText(notifText).setAutoCancel(true);
                //alamNotificationBuilder.setContentIntent(contentIntent);

                alarmNotificationManager.notify(NOTIFICATION_ID, alamNotificationBuilder.build());
            }
        }
    }
}
