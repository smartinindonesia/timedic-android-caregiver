package com.smartin.timedic.caregiver.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.smartin.timedic.caregiver.HealthCalculatorActivity;
import com.smartin.timedic.caregiver.model.uimodel.Slider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.screenslidefragment.ScreenSlideHomeFragment;

/**
 * Created by Hafid on 8/22/2017.
 */

public class HomeFragment extends Fragment {

    public static String TAG = "[HomeFragment]";

    @BindView(R.id.btnHomecare)
    ImageButton btnHomecare;
    @BindView(R.id.btnHealthCalculator)
    ImageButton btnHealthCalc;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    private PagerAdapter mPagerAdapter;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            Log.d(TAG, "Visible now");
            if (!isVisibleToUser) {
                Log.d(TAG, "Not visible anymore.  Stopping audio.");
                // TODO stop audio playback
            }
            else {
                Log.d(TAG, "Panggil Fungsi");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Integer index = getArguments().getInt("tab_index");
        //Log.i("HOMEFRAGMENT", index.toString() );
        View vwInflater = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, vwInflater);
        List<Slider> sliders = new ArrayList<>();
        sliders.add(new Slider(R.drawable.ads_01,""));
        sliders.add(new Slider(R.drawable.ads_02,""));
        sliders.add(new Slider(R.drawable.ads_03,""));
        sliders.add(new Slider(R.drawable.ads_04,""));
        sliders.add(new Slider(R.drawable.ads_05,""));

        mPagerAdapter = new HomeFragment.ScreenSlidePagerAdapter(getChildFragmentManager(), sliders);

        viewPager.setAdapter(mPagerAdapter);
        viewPager.canScrollHorizontally(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager, true);


        btnHomecare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), HomecareActivity.class);
                //startActivity(intent);
            }
        });

        btnHealthCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthCalculatorActivity.class);
                startActivity(intent);
            }
        });

        return vwInflater;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<Slider> sliderList;

        public ScreenSlidePagerAdapter(FragmentManager fm, List<Slider> sliders) {
            super(fm);
            this.sliderList = sliders;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("view_code", sliderList.get(position));
            Fragment afragment = new ScreenSlideHomeFragment();
            afragment.setArguments(bundle);
            return afragment;
        }

        @Override
        public int getCount() {
            return sliderList.size();
        }
    }

}
