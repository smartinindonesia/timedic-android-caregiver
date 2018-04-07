package com.smartin.timedic.caregiver.screenslidefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.model.uimodel.Slider;
import com.smartin.timedic.caregiver.tools.ViewFaceUtility;

/**
 * Created by Hafid on 8/21/2017.
 */

public class ScreenSlidePageFragment extends Fragment {
    public static String TAG = "[ScreenSlidePageFg]";

    @BindView(R.id.screenLogo)
    ImageView screenLogo;
    @BindView(R.id.screenMessage)
    TextView screenMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.screen_fragment_swipe, container, false);
        ButterKnife.bind(this, rootView);
        Slider code = (Slider) this.getArguments().getSerializable("view_code");
        screenLogo.setImageResource(code.getImage_source_id());
        screenMessage.setText(Html.fromHtml(code.getDescription()));
        setFonts();
        return rootView;
    }

    public void setFonts(){
        ViewFaceUtility.applyFont(screenMessage, getActivity(), "fonts/Dosis-Medium.otf");
    }

}
