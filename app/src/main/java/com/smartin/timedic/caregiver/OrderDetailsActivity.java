package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartin.timedic.caregiver.adapter.CaregiverHistoryAdapter;
import com.smartin.timedic.caregiver.customuicompt.RecyclerTouchListener;
import com.smartin.timedic.caregiver.manager.HomecareSessionManager;
import com.smartin.timedic.caregiver.model.CaregiverOrder;
import com.smartin.timedic.caregiver.model.HomecareOrder;
import com.smartin.timedic.caregiver.tools.ConverterUtility;
import com.smartin.timedic.caregiver.tools.TextFormatter;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;
import com.smartin.timedic.caregiver.tools.restservice.APIClient;
import com.smartin.timedic.caregiver.tools.restservice.HomecareTransactionAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {
    public static String TAG = "[OrderDetailsActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.serviceName)
    TextView serviceName;
    @BindView(R.id.orderNumber)
    TextView orderNumber;
    @BindView(R.id.transactionVisitDate)
    TextView transactionVisitDate;
    @BindView(R.id.transactionOrderDate)
    TextView transactionOrderDate;
    @BindView(R.id.downPayment)
    TextView downPayment;
    @BindView(R.id.totalApproxCash)
    TextView totalApproxCash;
    @BindView(R.id.totalCash)
    TextView totalCash;
    @BindView(R.id.addressLoc)
    TextView addressLoc;
    @BindView(R.id.mapLocation)
    TextView mapLocation;
    @BindView(R.id.transactionStatus)
    TextView transactionStatus;
    @BindView(R.id.caregiverHistory)
    RecyclerView caregiverHistory;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.downPaymentStatus)
    TextView downPaymentStatus;
    @BindView(R.id.totalCashStatus)
    TextView totalCashStatus;

    @BindView(R.id.orderNumberTitle)
    TextView orderNumberTitle;
    @BindView(R.id.transactionOrderDateTitle)
    TextView transactionOrderDateTitle;
    @BindView(R.id.transactionVisitDateTitle)
    TextView transactionVisitDateTitle;
    @BindView(R.id.downPaymentTitle)
    TextView downPaymentTitle;
    @BindView(R.id.totalApproxCashTitle)
    TextView totalApproxCashTitle;
    @BindView(R.id.totalCashTitle)
    TextView totalCashTitle;
    @BindView(R.id.addressLocTitle)
    TextView addressLocTitle;
    @BindView(R.id.transactionStatusTitle)
    TextView transactionStatusTitle;
    @BindView(R.id.caregiverHistoryTitle)
    TextView caregiverHistoryTitle;

    HomecareOrder homecareOrder;

    CaregiverHistoryAdapter caregiverHistoryAdapter;
    HomecareSessionManager homecareSessionManager;
    HomecareTransactionAPIInterface homecareTransactionAPIInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        createTitleBar();
        homecareOrder = (HomecareOrder) getIntent().getSerializableExtra("homecareOrder");
        fillPage();
        mapLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderDetailsActivity.this, MapViewerActivity.class);
                intent.putExtra("latitude", homecareOrder.getLocationLatitude());
                intent.putExtra("longitude", homecareOrder.getLocationLongitude());
                startActivity(intent);
            }
        });

        homecareSessionManager = new HomecareSessionManager(this, this);
        homecareTransactionAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getApplicationContext()).create(HomecareTransactionAPIInterface.class);

        caregiverHistoryAdapter = new CaregiverHistoryAdapter(this, this, homecareOrder.getCaregiverArrayList());
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        caregiverHistory.addItemDecoration(dividerItemDecoration);
        caregiverHistory.setLayoutManager(mLayoutManager);
        caregiverHistory.setItemAnimator(new DefaultItemAnimator());
        caregiverHistory.setAdapter(caregiverHistoryAdapter);
        caregiverHistory.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), caregiverHistory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CaregiverOrder cgs = caregiverHistoryAdapter.getItem(position);
                if (!cgs.getRateStatus()) {
                    Intent intent = new Intent(OrderDetailsActivity.this, RateCaregiverActivity.class);
                    intent.putExtra("caregiver", cgs);
                    intent.putExtra("order", homecareOrder);
                    startActivity(intent);
                } else {
                    Snackbar.make(mainLayout, "Rating sudah pernah dilakukan!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        setFonts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetails();
    }

    @SuppressLint("RestrictedApi")
    public void createTitleBar() {
        setSupportActionBar(toolbar);
        ViewFaceUtility.changeToolbarFont(toolbar, this, "fonts/BalooBhaina-Regular.ttf", R.color.theme_black);
        ActionBar mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
        mActionbar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionbar.setDisplayShowHomeEnabled(true);
        mActionbar.setDisplayShowTitleEnabled(true);
        mActionbar.setDisplayShowCustomEnabled(true);
    }

    public void fillPage() {
        serviceName.setText(homecareOrder.getSelectedService());
        orderNumber.setText(homecareOrder.getOrderNumber());
        transactionOrderDate.setText(ConverterUtility.getDateString(homecareOrder.getTransactionDate()));
        transactionVisitDate.setText(ConverterUtility.getDateString(homecareOrder.getDate()));
        downPayment.setText(TextFormatter.doubleToRupiah(homecareOrder.getPrepaidPrice()));
        totalApproxCash.setText(homecareOrder.getPredictionPrice());
        totalCash.setText(TextFormatter.doubleToRupiah(homecareOrder.getFixedPrice()));
        addressLoc.setText(homecareOrder.getFullAddress());
        mapLocation.setText("(" + homecareOrder.getLocationLatitude() + "," + homecareOrder.getLocationLongitude() + ")");
        transactionStatus.setText(homecareOrder.getHomecareTransactionStatus().getStatus());
        if (homecareOrder.getPaymentFixedPriceStatusId() != null) {
            totalCashStatus.setText(homecareOrder.getPaymentFixedPriceStatusId().getStatus());
            if (homecareOrder.getPaymentFixedPriceStatusId().getId() == 1){
                totalCashStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.green_button_background));
            } else if (homecareOrder.getPaymentFixedPriceStatusId().getId() == 2) {
                totalCashStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.red_button_background));
            }
        }
        if (homecareOrder.getPaymentPrepaidPriceStatusId() != null) {
            downPaymentStatus.setText(homecareOrder.getPaymentPrepaidPriceStatusId().getStatus());
            if (homecareOrder.getPaymentPrepaidPriceStatusId().getId() == 1){
                downPaymentStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.green_button_background));
            } else if (homecareOrder.getPaymentPrepaidPriceStatusId().getId() == 2) {
                downPaymentStatus.setBackground(ContextCompat.getDrawable(this, R.drawable.red_button_background));
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void getOrderDetails() {
        Call<HomecareOrder> resp = homecareTransactionAPIInterface.getTransactionById(homecareOrder.getId());
        resp.enqueue(new Callback<HomecareOrder>() {
            @Override
            public void onResponse(Call<HomecareOrder> call, Response<HomecareOrder> response) {
                homecareOrder = response.body();
                caregiverHistoryAdapter = new CaregiverHistoryAdapter(OrderDetailsActivity.this, getApplicationContext(), homecareOrder.getCaregiverArrayList());
                caregiverHistory.setAdapter(caregiverHistoryAdapter);
                fillPage();
            }

            @Override
            public void onFailure(Call<HomecareOrder> call, Throwable t) {

            }
        });
    }

    private void setFonts() {
        ViewFaceUtility.applyFont(serviceName, this, "fonts/Dosis-Bold.otf");
        ArrayList<TextView> arrayList = new ArrayList<>();

        arrayList.add(transactionVisitDate);
        arrayList.add(transactionOrderDate);
        arrayList.add(downPayment);
        arrayList.add(totalCash);
        arrayList.add(addressLoc);
        arrayList.add(mapLocation);
        arrayList.add(transactionStatus);
        arrayList.add(downPaymentStatus);
        arrayList.add(totalCashStatus);
        arrayList.add(orderNumber);

        arrayList.add(transactionVisitDateTitle);
        arrayList.add(transactionOrderDateTitle);
        arrayList.add(downPaymentTitle);
        arrayList.add(totalApproxCashTitle);
        arrayList.add(totalCashTitle);
        arrayList.add(addressLocTitle);
        arrayList.add(transactionStatusTitle);
        arrayList.add(caregiverHistoryTitle);
        arrayList.add(orderNumberTitle);

        ViewFaceUtility.applyFonts(arrayList, this, "fonts/Dosis-Medium.otf");
    }
}

