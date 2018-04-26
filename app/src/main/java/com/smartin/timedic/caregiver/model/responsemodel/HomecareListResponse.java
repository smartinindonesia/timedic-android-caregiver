package com.smartin.timedic.caregiver.model.responsemodel;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import com.smartin.timedic.caregiver.model.HomecareOrder;
import okhttp3.ResponseBody;

/**
 * Created by Hafid on 4/22/2018.
 */

public class HomecareListResponse {
    public static final String TAG = "HomecareListResponse";

    private ResponseBody response;
    private ArrayList<HomecareOrder> homecareOrders;
    private int numberOfRow;
    private Gson g = new Gson();

    public void convertResponse() throws JSONException, IOException {
        JSONArray arr = new JSONArray(response.string());
        String homecareOrderArr = arr.getJSONArray(0).toString();
        Log.i(TAG, homecareOrderArr);
        String norItem = arr.getJSONObject(1).toString();
        Log.i(TAG, norItem);
        TypeToken<ArrayList<HomecareOrder>> token = new TypeToken<ArrayList<HomecareOrder>>() {};
        homecareOrders = g.fromJson(homecareOrderArr, token.getType());
        PageItem p = g.fromJson(norItem, PageItem.class);
        numberOfRow = p.getNumOfRows();
    }

    public ResponseBody getResponse() {
        return response;
    }

    public void setResponse(ResponseBody response) {
        this.response = response;
    }

    public ArrayList<HomecareOrder> getHomecareOrders() {
        return homecareOrders;
    }

    public void setHomecareOrders(ArrayList<HomecareOrder> homecareOrders) {
        this.homecareOrders = homecareOrders;
    }

    public int getNumberOfRow() {
        return numberOfRow;
    }

    public void setNumberOfRow(int numberOfRow) {
        this.numberOfRow = numberOfRow;
    }

    public class PageItem {
        @SerializedName("numOfRows")
        private Integer numOfRows;

        public Integer getNumOfRows() {
            return numOfRows;
        }

        public void setNumOfRows(Integer numOfRows) {
            this.numOfRows = numOfRows;
        }
    }
}
