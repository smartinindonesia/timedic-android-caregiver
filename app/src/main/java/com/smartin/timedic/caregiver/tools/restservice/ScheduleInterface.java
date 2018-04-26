package com.smartin.timedic.caregiver.tools.restservice;

import com.smartin.timedic.caregiver.config.Constants;
import com.smartin.timedic.caregiver.model.parammodel.ScheduleParam;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Hafid on 1/2/2018.
 */

public interface ScheduleInterface {


    @POST(Constants.ROUTE_ADD_SCHEDULE)
    Call<ResponseBody> addSchedule(@Body ScheduleParam param);

    @POST(Constants.ROUTE_CHECK_SCHEDULE_DATA)
    Call<ResponseBody> cekScheduleData(@Query("idCaregiver") Long idCaregiver, @Query("day") String day);

    @POST(Constants.ROUTE_CHECK_SCHEDULE_STATUS)
    Call<ResponseBody> cekScheduleStatus(@Query("idCaregiver") Long idCaregiver);

    @PUT(Constants.ROUTE_UPDATE_SCHEDULE_ID_CAREGIVER_AND_DAY)
    Call<ResponseBody> updateScheduleStatusByIdCaregiverAndDay(@Query("idCaregiver") Long idCaregiver, @Query("day") String day, @Query("status") Boolean status);

    @PUT(Constants.ROUTE_UPDATE_SCHEDULE_FOR_TIME)
    Call<ResponseBody> updateScheduleForTime(@Query("id") Long id, @Query("startTime1") String startTime1, @Query("startTime2") String startTime2, @Query("endTime1") String endTime1, @Query("endTime2") String endTime2);

    @GET(Constants.ROUTE_SCHEDULE_BY_ID + "{id}")
    Call<ResponseBody> getScheduleById(@Path(value = "id", encoded = true) Long id);

    /*
    @POST(Constants.ROUTE_LOGIN_CAREGIVER)
    Call<LoginResponse> loginUser(@Query("username") String username, @Query("password") String password);

    @POST(Constants.ROUTE_LOGIN_FIREBASE)
    Call<LoginResponse> loginUserWithFirebase(@Query("firebaseId") String firebaseId, @Query("type") String type);

    @PUT(Constants.ROUTE_CAREGIVER_BY_ID+ "{id}")
    Call<ResponseBody> updateUser(@Path(value = "id", encoded = true) Long id, @Body User user);

    @PUT(Constants.ROUTE_CAREGIVER_BY_ID + "{id}")
    Call<ResponseBody> updateProfile(@Path(value = "id", encoded = true) Long id, @Body UserProfile user);

    @PUT(Constants.ROUTE_CAREGIVER_BY_ID + "{id}")
    Call<ResponseBody> updatePassword(@Path(value = "id", encoded = true) Long id, @Body PasswordProfile pass);

    @GET(Constants.ROUTE_CAREGIVER_BY_ID + "{id}")
    Call<User> getProfile(@Path(value = "id", encoded = true) Long id);
    */
}
