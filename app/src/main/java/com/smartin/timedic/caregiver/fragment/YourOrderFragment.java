package com.smartin.timedic.caregiver.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.fragment.yourorderchildfragment.ActiveOrder;
import com.smartin.timedic.caregiver.fragment.yourorderchildfragment.HistoryOrder;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

/**
 * Created by Hafid on 8/22/2017.
 */

public class YourOrderFragment extends Fragment {
    public static final String TAG = "[YourOrderFragment]";

    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_your_order, container, false);
        ButterKnife.bind(this, layoutView);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        // Inflate the layout for this fragment
        setFonts();
        return layoutView;
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        // Make sure that we are currently visible
        if (this.isVisible()) {
            Log.d(TAG, "Visible now");
            if (isVisibleToUser && isResumed()) {
                Log.d(TAG, "Not visible anymore.  Stopping audio.");
                // TODO stop audio playback
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        YourOrderFragment.ViewPagerAdapter adapter = new YourOrderFragment.ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new ActiveOrder(), "Order Aktif");
        adapter.addFragment(new HistoryOrder(), "Riwayat Order");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setFonts(){
        setTabsFont();
    }

    public void setTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();

        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);

            int tabChildsCount = vgTab.getChildCount();

            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    //Put your font in assests folder
                    //assign name of the font here (Must be case sensitive)
                    ViewFaceUtility.applyFont((TextView) tabViewChild, getActivity(), "fonts/BalooBhaina-Regular.ttf");
                }
            }
        }
    }
}
