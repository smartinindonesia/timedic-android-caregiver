package com.smartin.timedic.caregiver.config;

import com.smartin.timedic.caregiver.R;
import com.smartin.timedic.caregiver.model.GenderOption;
import com.smartin.timedic.caregiver.model.Religion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hafid on 02/05/2018.
 */

public class ConstantVals {
    public static List<Religion> getReligionList() {
        List<Religion> religions = new ArrayList<>();
        religions.add(new Religion((long) 1, "Islam"));
        religions.add(new Religion((long) 2, "Kristen"));
        religions.add(new Religion((long) 3, "Katolik"));
        religions.add(new Religion((long) 4, "Hindu"));
        religions.add(new Religion((long) 5, "Budha"));
        religions.add(new Religion((long) 6, "Kong Hu Cu"));
        religions.add(new Religion((long) 7, "Tidak Ada"));
        return religions;
    }

    public static List<GenderOption> getGenders() {
        List<GenderOption> genderOptions = new ArrayList<>();
        genderOptions.add(new GenderOption(R.drawable.btn_laki_laki, "Laki-Laki"));
        genderOptions.add(new GenderOption(R.drawable.btn__perempuan, "Perempuan"));
        return genderOptions;
    }
}
