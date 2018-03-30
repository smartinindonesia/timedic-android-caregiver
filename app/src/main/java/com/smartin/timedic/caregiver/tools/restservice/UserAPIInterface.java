package com.smartin.timedic.caregiver.tools.restservice;

import com.smartin.timedic.caregiver.config.Constants;
import com.smartin.timedic.caregiver.model.User;
import com.smartin.timedic.caregiver.model.parammodel.PasswordProfile;
import com.smartin.timedic.caregiver.model.parammodel.RegisterParam;
import com.smartin.timedic.caregiver.model.parammodel.UserProfile;
import com.smartin.timedic.caregiver.model.responsemodel.LoginResponse;
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

public interface UserAPIInterface {
    @POST(Constants.ROUTE_LOGIN)
    Call<LoginResponse> loginUser(@Query("username") String username, @Query("password") String password);

    @POST(Constants.ROUTE_RESGISTER)
    Call<ResponseBody> registerUser(@Body RegisterParam param);

    @PUT(Constants.ROUTE_USER_BY_ID + "{id}")
    Call<ResponseBody> updateUser(@Path(value = "id", encoded = true) Long id, @Body User user);

    @PUT(Constants.ROUTE_USER_BY_ID + "{id}")
    Call<ResponseBody> updateProfile(@Path(value = "id", encoded = true) Long id, @Body UserProfile user);

    @PUT(Constants.ROUTE_USER_BY_ID + "{id}")
    Call<ResponseBody> updatePassword(@Path(value = "id", encoded = true) Long id, @Body PasswordProfile pass);

    @GET(Constants.ROUTE_USER_BY_ID + "{id}")
    Call<User> getProfile(@Path(value = "id", encoded = true) Long id);
}
