package com.smartin.timedic.caregiver.tools.restservice;

import android.content.Context;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import com.smartin.timedic.caregiver.config.Constants;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hafid on 1/2/2018.
 */

public class APIClient {
    public static final String TAG = "[APIClient]";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }


    public static Retrofit getClientWithToken(HomecareSessionManager sessionManager, Context context) {
        CookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        HttpLoggingInterceptor loggingHeader = new HttpLoggingInterceptor();
        HttpLoggingInterceptor loggingBody = new HttpLoggingInterceptor();
        loggingHeader.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        loggingBody.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(new SessionRequestInterceptor(sessionManager))
                .authenticator(new AuthenticatorSession(sessionManager))
                .addInterceptor(loggingHeader)
                .addInterceptor(loggingBody)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}