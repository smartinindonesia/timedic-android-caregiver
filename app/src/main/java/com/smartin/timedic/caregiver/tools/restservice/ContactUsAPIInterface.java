package com.smartin.timedic.caregiver.tools.restservice;

import com.smartin.timedic.caregiver.config.Constants;
import com.smartin.timedic.caregiver.model.ContactRes;
import com.smartin.timedic.caregiver.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hafid on 02/05/2018.
 */

public interface ContactUsAPIInterface {
    @GET(Constants.ROUTE_CONTACT + "{id}")
    Call<ContactRes> getContactDetails(@Path(value = "id", encoded = true) Long id);
}
