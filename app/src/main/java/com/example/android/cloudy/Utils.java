package com.example.android.cloudy;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ccu17 on 24/04/2017.
 */

public class Utils {

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();

    }


}
