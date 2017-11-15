package brainandcognitionlab.bac.Controller.DB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by hojaeson on 5/19/16.
 */
public class Date {
    public String getDate() {
        Calendar date = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.KOREA);
        String bookingDate = formatter.format(date.getTime());
        //YYYY-MM-DD HH:MM:SS
        return bookingDate;
    }

}
