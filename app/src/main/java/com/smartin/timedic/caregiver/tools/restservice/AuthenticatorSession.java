package com.smartin.timedic.caregiver.tools.restservice;

import java.io.IOException;

import javax.annotation.Nullable;

import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * Created by Hafid on 1/2/2018.
 */

public class AuthenticatorSession implements Authenticator {

    private final HomecareSessionManager sessionManager;

    public AuthenticatorSession(HomecareSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        return response.request().newBuilder()
                .header("Authorization", sessionManager.getToken())
                //.header("Cache-Control", "no-cache")
                .removeHeader("Accept-Encoding")
                .removeHeader("User-Agent")
                .removeHeader("Connection")
                .build();
    }
}
