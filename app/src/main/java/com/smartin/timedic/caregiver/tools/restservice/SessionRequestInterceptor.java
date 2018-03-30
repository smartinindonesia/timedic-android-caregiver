package com.smartin.timedic.caregiver.tools.restservice;

import android.util.Log;

import java.io.IOException;

import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Hafid on 1/2/2018.
 */

public class SessionRequestInterceptor implements Interceptor {
    public static final String TAG = "[SessionRequestInter]";
    private HomecareSessionManager sessionManager;

    public SessionRequestInterceptor() {

    }

    public SessionRequestInterceptor(HomecareSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.i(TAG, "Interceptor called " + request.headers().toString());
        Request newRequest = request.newBuilder()
                .header("Authorization", sessionManager.getToken())
                //.header("Cache-Control", "no-cache")
                .removeHeader("Accept-Encoding")
                .removeHeader("User-Agent")
                .removeHeader("Connection")
                .build();
        Log.i(TAG, "Interceptor called -" + newRequest.headers().toString());
        return chain.proceed(newRequest);
    }
}
