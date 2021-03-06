package com.smartin.timedic.caregiver.service;

import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.smartin.timedic.caregiver.R;

public class FireBaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "[FireBaseMessaging]";

    public FireBaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setSmallIcon(R.mipmap.ic_launcher_caregiver_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher_caregiver_round))
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true);
    }
}
