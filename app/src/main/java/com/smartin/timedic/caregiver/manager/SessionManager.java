package com.smartin.timedic.caregiver.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import com.smartin.timedic.caregiver.model.AppSetting;
import com.smartin.timedic.caregiver.model.User;

/**
 * Created by Hafid on 9/11/2017.
 */

public class SessionManager {
    public static String TAG = "[SessionManager]";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private Activity activity;

    public SessionManager(Context context, String KEY_PREFERENCES_NAME) {
        this.context = context;
        pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public SessionManager(Activity activity, Context context, String KEY_PREFERENCES_NAME) {
        this.activity = activity;
        this.context = context;
        pref = context.getSharedPreferences(KEY_PREFERENCES_NAME, context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(User user, String KEY_USER_INFOS_JSON, String KEY_IS_LOGIN, String KEY_USER_TOKEN, String token, String KEY_APP_SETTING) {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(user);
        //AppSetting appSetting = new AppSetting();
        //String appsSet = gson.toJson(appSetting);
        editor.putString(KEY_USER_INFOS_JSON, jsonInString);
        editor.putString(KEY_USER_TOKEN, token);
        //editor.putString(KEY_APP_SETTING, appsSet);
        editor.putBoolean(KEY_IS_LOGIN, true);
        editor.commit();
    }

    public void updateUserInfo(User user, String KEY_USER_INFOS_JSON) {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(user);
        editor.putString(KEY_USER_INFOS_JSON, jsonInString);
        editor.commit();
    }

    public User getUserDetail(String KEY_USER_INFOS_JSON) {
        Gson gson = new Gson();
        String jsonInString = pref.getString(KEY_USER_INFOS_JSON, "");
        User user = gson.fromJson(jsonInString, User.class);
        return user;
    }

    public void setAppSetting(AppSetting appSetting, String KEY_APP_SETTING) {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(appSetting);
        editor.putString(KEY_APP_SETTING, jsonInString);
        editor.commit();
    }

    public AppSetting getAppSetting(String KEY_APP_SETTING) {
        Gson gson = new Gson();
        String jsonInString = pref.getString(KEY_APP_SETTING, "");
        if (!jsonInString.equals("")) {
            AppSetting setting = gson.fromJson(jsonInString, AppSetting.class);
            return setting;
        } else {
            AppSetting setting = new AppSetting();
            setting.setActive(true);
            return setting;
        }
    }

    public String getToken(String KEY_USER_TOKEN) {
        String jsonInString = pref.getString(KEY_USER_TOKEN, "");
        return jsonInString;
    }

    public void clearToken(String KEY_USER_TOKEN) {
        editor.putString(KEY_USER_TOKEN, "");
        editor.commit();
    }

    public boolean hasToken(String KEY_USER_TOKEN) {
        String jsonInString = pref.getString(KEY_USER_TOKEN, "");
        return !jsonInString.equals("");
    }

    public boolean isLogin(String KEY_IS_LOGIN) {
        return pref.getBoolean(KEY_IS_LOGIN, false);
    }

    public void logout(String KEY_IS_LOGIN) {
        editor.putBoolean(KEY_IS_LOGIN, false);
        editor.commit();
        activity.finish();
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void setPref(SharedPreferences pref) {
        this.pref = pref;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
