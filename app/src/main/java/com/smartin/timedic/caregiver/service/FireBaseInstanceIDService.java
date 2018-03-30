package com.smartin.timedic.caregiver.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.User;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.UserAPIInterface;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FireBaseInstanceIDService extends FirebaseInstanceIdService {
    public static final String TAG = "[FireBaseInstanceID]";
    private HomecareSessionManager homecareSessionManager;
    private UserAPIInterface userAPIInterface;

    public FireBaseInstanceIDService() {
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String initialFCMToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Refreshed token: " + initialFCMToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(initialFCMToken);
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        homecareSessionManager = new HomecareSessionManager(getApplicationContext());
        if (homecareSessionManager.isLogin()) {
            User user = homecareSessionManager.getUserDetail();
            user.setFcmToken(token);
            userAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(UserAPIInterface.class);
            Call<ResponseBody> services = userAPIInterface.updateUser(user.getId(), user);
            services.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.i(TAG, "FCM Token Updated");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i(TAG, "Update FCM Token Failed");
                }
            });
        }
    }

}
