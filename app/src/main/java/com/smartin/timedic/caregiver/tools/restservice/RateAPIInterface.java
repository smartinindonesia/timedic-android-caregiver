package com.smartin.timedic.caregiver.tools.restservice;

import java.util.ArrayList;

import com.smartin.timedic.caregiver.config.Constants;
import com.smartin.timedic.caregiver.model.CaregiverRate;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RateAPIInterface {
    @GET(Constants.ROUTE_RATE_GET+"{id}")
    Call<ArrayList<CaregiverRate>> getRateByIdCaregiver(@Path(value = "id", encoded = true) Long id);
    @POST(Constants.ROUTE_RATE_POST)
    Call<ResponseBody> submetRate(@Body CaregiverRate rate);
}
