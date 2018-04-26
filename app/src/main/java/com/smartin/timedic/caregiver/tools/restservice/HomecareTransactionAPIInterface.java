package com.smartin.timedic.caregiver.tools.restservice;

import java.util.List;

import com.smartin.timedic.caregiver.config.Constants;
import com.smartin.timedic.caregiver.model.HomecareOrder;
import com.smartin.timedic.caregiver.model.parammodel.HomecareTransParam;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Hafid on 1/20/2018.
 */

public interface HomecareTransactionAPIInterface {

    @GET(Constants.ROUTE_TRANSACTION)
    Call<ResponseBody> getAllTransaction();

    @GET(Constants.ROUTE_TRANSACTION + "{id}")
    Call<HomecareOrder> getTransactionById(@Path(value = "id", encoded = true) Long id);

    @DELETE(Constants.ROUTE_TRANSACTION + "{id}")
    Call<ResponseBody> deleteTransactionById(@Path(value = "id", encoded = true) Long id);

    @POST(Constants.ROUTE_TRANSACTION)
    Call<ResponseBody> insertNewTransaction(@Body HomecareTransParam param);

    @GET(Constants.ROUTE_HISTORY_ORDER + "{idUser}")
    Call<List<HomecareOrder>> getHistoryOrderByIdUser(@Path(value = "idUser", encoded = true) Long id);

    @GET(Constants.ROUTE_ACTIVE_ORDER + "{id}")
    Call<List<HomecareOrder>> getActiveOrder(@Path(value = "id", encoded = true) Long id);

    @GET(Constants.ROUTE_ACTIVE_ORDER_PAGINATION)//page, size, sort, sortField, idUser
    Call<ResponseBody> getActiveOrderPage(@Query("page") Integer page, @Query("size") Integer size, @Query("sort") String sortType, @Query("sortField") String sortField, @Query("idCaregiver") Long idUser);

    @GET(Constants.ROUTE_HISTORY_ORDER_PAGINATION)//page, size, sort, sortField, idUser
    Call<ResponseBody> getHistoryOrderPage(@Query("page") Integer page, @Query("size") Integer size, @Query("sort") String sortType, @Query("sortField") String sortField, @Query("idCaregiver") Long idUser);
}
