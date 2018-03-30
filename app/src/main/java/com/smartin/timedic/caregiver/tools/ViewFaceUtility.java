package com.smartin.timedic.caregiver.tools;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
}
