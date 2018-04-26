package com.smartin.timedic.caregiver.tools.restservice;

import com.smartin.timedic.caregiver.model.Caregiver;
import com.smartin.timedic.caregiver.config.Constants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hafid on 3/27/2018.
 */

public interface HomecareCaregiverAPIInterface {
    @GET(Constants.ROUTE_CAREGIVER + "{id}")
    Call<Caregiver> getCaregiver(@Path(value = "id", encoded = true) Long id);
}
