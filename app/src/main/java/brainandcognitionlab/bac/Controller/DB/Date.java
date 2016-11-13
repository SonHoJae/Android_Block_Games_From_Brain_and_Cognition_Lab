package brainandcognitionlab.bac.Controller.DB;

import java.util.Calendar;

/**
 * Created by hojaeson on 5/19/16.
 */
public class Date {
    public String getDate()
    {
        Calendar calendar = Calendar.getInstance();
        //YYYY-MM-DD HH:MM:SS
        return String.valueOf(calendar.get(Calendar.YEAR))+"-"+String.valueOf(calendar.get(Calendar.MONTH))+"-"+String.valueOf(calendar.get(Calendar.DATE))
                +" "+String.valueOf(calendar.get(Calendar.HOUR))+":"+String.valueOf(calendar.get(Calendar.MINUTE))+":"+String.valueOf(calendar.get(Calendar.SECOND));
    }

}
