package com.amritha.acadgild.android_project2_expensemanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Amritha on 4/20/18.
 */
public class Function {

    //creating method for Date format

    public static String Epoch2DateString(String epochSeconds, String formatString) {
        Date updatedate = new Date(Long.parseLong(epochSeconds));
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(updatedate);
    }

    //creating method for updating date

    public static Calendar Epoch2Calender(String epochSeconds) {
        Date updatedate = new Date(Long.parseLong(epochSeconds));
        Calendar cal = Calendar.getInstance();
        cal.setTime(updatedate);

        return cal;
    }

}
