package com.smartin.timedic.caregiver.fragment.yourorderchildfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.adapter.HistoryOrderAdapter;
import com.smartin.timedic.caregiver.customuicompt.EndlessScrollListener;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.HomecareOrder;
import com.smartin.timedic.caregiver.model.responsemodel.HomecareListResponse;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.HomecareTransactionAPIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hafid on 11/25/2017.
 */

public class HistoryOrder extends Fragment {
    public static String TAG = "[HistoryOrder]";

    @BindView(R.id.active_history_list)
    RecyclerView recyclerView;
    @BindView(R.id.loadingBar)
    ProgressBar loadingBar;

    private boolean _hasLoadedOnce = false;

    private HomecareSessionManager homecareSessionManager;
    private List<HomecareOrder> homecareOrderList = new ArrayList<>();
    private HistoryOrderAdapter historyOrderAdapter;
    private HomecareTransactionAPIInterface homecareTransactionAPIInterface;

    //PAGINATION
    private Integer page = 0;
    private Integer sizePerPage = 6;
    private Integer maxPage = 0;
    private boolean isLoading = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //resetData();
        //getHistoryOrder()
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_order, container, false);
        homecareSessionManager = new HomecareSessionManager(getActivity(), getContext());
        homecareTransactionAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getContext()).create(HomecareTransactionAPIInterface.class);
        ButterKnife.bind(this, view);
        homecareSessionManager = new HomecareSessionManager(getActivity(), getContext());
        historyOrderAdapter = new HistoryOrderAdapter(getActivity(), getContext(), homecareOrderList);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(historyOrderAdapter);
        recyclerView.setOnScrollListener(new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchNext();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void getHistoryOrderPagination() {
        loadingBar.setVisibility(View.VISIBLE);
        Call<ResponseBody> services = homecareTransactionAPIInterface.getHistoryOrderPage(page, sizePerPage, "DESC", "id", homecareSessionManager.getUserDetail().getId());
        services.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        HomecareListResponse homecareListResponse = new HomecareListResponse();
                        homecareListResponse.setResponse(response.body());
                        homecareListResponse.convertResponse();
                        historyOrderAdapter.addAll(homecareListResponse.getHomecareOrders());
                        historyOrderAdapter.notifyDataSetChanged();
                        maxPage = (int) Math.round(homecareListResponse.getNumberOfRow() / sizePerPage);
                        Log.i(TAG, maxPage + "Number of pages");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, response.body().toString());
                } else {
                    Log.i(TAG, "Code FAILURE");
                }
                loadingBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                loadingBar.setVisibility(View.GONE);
                homecareSessionManager.logout();
            }
        });
    }

    public void fetchPrev() {
        if (this.page > 0) {
            this.page--;
            this.getHistoryOrderPagination();
        }
    }

    public void fetchNext() {
        if (this.page < (this.maxPage)) {
            this.page++;
            this.getHistoryOrderPagination();
        }
    }

    public void getHistoryOrder() {
        Call<List<HomecareOrder>> services = homecareTransactionAPIInterface.getHistoryOrderByIdUser(homecareSessionManager.getUserDetail().getId());
        services.enqueue(new Callback<List<HomecareOrder>>() {
            @Override
            public void onResponse(Call<List<HomecareOrder>> call, Response<List<HomecareOrder>> response) {
                if (response.code() == 200) {
                    List<HomecareOrder> responseHomecareOrder = response.body();
                    homecareOrderList.clear();
                    for (int i = 0; i < responseHomecareOrder.size(); i++) {
                        homecareOrderList.add(responseHomecareOrder.get(i));
                    }
                    historyOrderAdapter.notifyDataSetChanged();
                    Log.i(TAG, response.body().toString());
                } else {
                    Log.i(TAG, "Code FAILURE");
                }
            }

            @Override
            public void onFailure(Call<List<HomecareOrder>> call, Throwable t) {
                call.cancel();
                homecareSessionManager.logout();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);

        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                resetData();
                _hasLoadedOnce = true;
            }
        }
    }

    private void resetData(){
        page = 0;
        homecareOrderList.clear();
        getHistoryOrderPagination();
    }
}
