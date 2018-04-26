package com.smartin.timedic.caregiver.tools;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hafid on 2/15/2018.
 */

public class ViewFaceUtility {
    public static void changeToolbarFont(Toolbar toolbar, Activity context, String path, int res_color) {
        toolbar.setTitleTextColor(context.getResources().getColor(res_color));
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv, context, path);
                    break;
                }
            }
        }
    }

    public static void applyFont(TextView tv, Activity context, String path) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), path));
    }

    public static void applyTextInputLayout(TextInputLayout tl, Activity context, String path ){
        tl.setTypeface(Typeface.createFromAsset(context.getAssets(), path));
    }

    public static void applyTextInputLayouts(ArrayList<TextInputLayout> tls, Activity context, String path ){
        for (int i = 0; i < tls.size();i++) {
            tls.get(i).setTypeface(Typeface.createFromAsset(context.getAssets(), path));
        }
    }

    public static void applyFonts(ArrayList<TextView> tvs, Activity context, String path) {
        for (int i = 0; i < tvs.size();i++) {
            tvs.get(i).setTypeface(Typeface.createFromAsset(context.getAssets(), path));
        }
    }
}
