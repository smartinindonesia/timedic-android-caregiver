package com.smartin.timedic.caregiver.screenslidefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.model.uimodel.Slider;

/**
 * Created by Hafid on 2/9/2018.
 */

public class ScreenSlideHomeFragment extends Fragment {
    public static String TAG = "[ScreenSlideHomeFg]";

    @BindView(R.id.bannerImage)
    ImageView bannerImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.banner_fragment_swipe, container, false);
        ButterKnife.bind(this,rootView);
        Slider code = (Slider) this.getArguments().getSerializable("view_code");
        bannerImage.setImageResource(code.getImage_source_id());
        return rootView;
    }
}
