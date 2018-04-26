package com.smartin.timedic.caregiver.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartin.timedic.caregiver.R;


public class Trx extends Fragment {

    public static String TAG = "[Trx]";

    public Trx() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View newView = inflater.inflate(R.layout.fragment_trx, container, false);

        return newView;
    }
}
