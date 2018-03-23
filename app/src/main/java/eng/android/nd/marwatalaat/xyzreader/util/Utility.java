package eng.android.nd.marwatalaat.xyzreader.util;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by MarwaTalaat on 4/6/2017.
 */

public class Utility {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");
    // Use default locale format
    private static SimpleDateFormat outputFormat = new SimpleDateFormat();

    public static Date parsePublishedDate(String published_date) {
        try {
            return dateFormat.parse(published_date);
        } catch (ParseException ex) {
            Log.e(TAG, ex.getMessage());
            Log.i(TAG, "passing today's date");
            return new Date();
        }
    }


    public static String parseTitleBefore(String published_date, String author) {
        return
                DateUtils.getRelativeTimeSpanString(
                        parsePublishedDate(published_date).getTime(),
                        System.currentTimeMillis(), DateUtils.HOUR_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_ALL).toString()
                        + "<br/>" + " by "
                        + author;
    }

    public static String parseTitleAfter(String published_date, String author) {
        return outputFormat.format(parsePublishedDate(published_date))
                + "<br/>" + " by "
                + author;

    }

    public static String [] getArticleBodyLinesCount(String body){
        String[] split = body.split("\\r\\n");

        return  split;

    }
}
