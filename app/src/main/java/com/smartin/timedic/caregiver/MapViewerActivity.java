package com.smartin.timedic.caregiver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

public class MapViewerActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static String TAG = "[MapViewerActivity]";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private GoogleMap googleMap;
    private LatLng myLocation;
    private Marker currentLocationMarker;
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_viewer);
        ButterKnife.bind(this);
        initilizeMap();
        createTitleBar();
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


    private void initilizeMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude",0.0f);
        longitude = intent.getDoubleExtra("longitude",0.0f);
        myLocation = new LatLng(latitude, longitude);
        currentLocationMarker = this.googleMap.addMarker(new MarkerOptions().draggable(false).position(myLocation).title("Set My Location"));
        currentLocationMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_myloc));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        this.googleMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
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
}
