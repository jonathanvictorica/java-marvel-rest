package com.jmg.marvel.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {

    public static String formatDateTime(Date datetime) {
        return new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(datetime);
    }

    public static long getDiffHoursWithNow(Date datetime){
        long diff = new Date().getTime() - datetime.getTime();
        return TimeUnit.MILLISECONDS.toHours(diff);
    }
}
