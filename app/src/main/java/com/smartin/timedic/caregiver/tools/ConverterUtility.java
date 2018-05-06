package com.smartin.timedic.caregiver.tools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hafid on 1/7/2018.
 */

public class ConverterUtility {

    public static String getDateString(int timeStamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "-";
        }
    }

    public static Long getTimeStamp(String date, String dateformat) {
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(
                dateformat);
        Date lFromDate1 = null;
        try {
            lFromDate1 = datetimeFormatter1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("gpsdate :" + lFromDate1);
        Timestamp fromTS1 = new Timestamp(lFromDate1.getTime());
        return fromTS1.getTime();
    }

    public static String getDateString(Long timeStamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "-";
        }
    }

    public static String getDateStringCustomPattern(Long timeStamp, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        } catch (Exception ex) {
            return "-";
        }
    }
}
