package com.smartin.timedic.caregiver.tools.restservice;

import com.smartin.timedic.caregiver.config.Constants;
import com.smartin.timedic.caregiver.model.User;
import com.smartin.timedic.caregiver.model.parammodel.FCMServerParam;
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
    @POST(Constants.ROUTE_LOGIN_CAREGIVER)
    Call<LoginResponse> loginUser(@Query("email") String email, @Query("password") String password);

    @POST(Constants.ROUTE_REGISTER_CAREGIVER)
    Call<ResponseBody> registerUser(@Body RegisterParam param);

    @POST(Constants.ROUTE_LOGIN_FIREBASE)
    Call<LoginResponse> loginUserWithFirebase(@Query("firebaseId") String firebaseId, @Query("type") String type);

    @POST(Constants.ROUTE_CHECK_PASSWORD_IS_NULL_OR_NOT)
    Call<ResponseBody> checkCaregiverPasswordIsNullOrNot(@Query("email") String email);

    @POST(Constants.ROUTE_LOGIN_FIREBASE_TOKEN)
    Call<LoginResponse> loginUserWithFirebaseToken(@Query("firebaseToken") String firebaseId, @Query("type") String type);

    @POST(Constants.ROUTE_GET_URL_BY_ID_CAREGIVER)
    Call<ResponseBody> getUrlById(@Query("idCaregiver") String idCaregiver, @Query("type") String type);

    @PUT(Constants.ROUTE_CAREGIVER_BY_ID+ "{id}")
    Call<ResponseBody> updateUser(@Path(value = "id", encoded = true) Long id, @Body User user);

    @PUT(Constants.ROUTE_CAREGIVER_BY_ID+ "{id}")
    Call<ResponseBody> updateFCMTokenUser(@Path(value = "id", encoded = true) Long id, @Body FCMServerParam user);

    @PUT(Constants.ROUTE_CAREGIVER_BY_ID + "{id}")
    Call<ResponseBody> updateProfile(@Path(value = "id", encoded = true) Long id, @Body UserProfile user);

    @PUT(Constants.ROUTE_CAREGIVER_BY_ID + "{id}")
    Call<ResponseBody> updatePassword(@Path(value = "id", encoded = true) Long id, @Body PasswordProfile pass);

    @GET(Constants.ROUTE_CAREGIVER_BY_ID + "{id}")
    Call<User> getProfile(@Path(value = "id", encoded = true) Long id);
}
