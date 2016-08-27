package com.codepath.apps.mysimpletweets.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by sdass on 8/20/16.
 */
public class ParseRelativeDate {
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (relativeDate.contains("year")){
            relativeDate = relativeDate.replace("year","y");
        }
        if (relativeDate.contains("month")){
            relativeDate = relativeDate.replace("month","m");
        }
        if (relativeDate.contains("day")){
            relativeDate = relativeDate.replace("day","d");
        }
        if (relativeDate.contains("hour")){
            relativeDate = relativeDate.replace("hour","h");
        }
        if (relativeDate.contains("minute")){
            relativeDate = relativeDate.replace("minute","min");
        }
        if (relativeDate.contains("seconds")){
            relativeDate = relativeDate.replace("seconds","s");
        }
        return relativeDate;
    }
}
