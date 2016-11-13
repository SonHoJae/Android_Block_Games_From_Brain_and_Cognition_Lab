package brainandcognitionlab.bac.Controller.Timer;

import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hojaeson on 4/16/16.
 */
public class TimerHandler extends Handler {
    TextView timerView;
    Timer timer;
    final Handler myHandler = new Handler();
    int milli = 0;
    int sec = 0;
    int min = 0;
    boolean isRunning = false;
    public TimerHandler(TextView timerView)
    {
        this.timerView = timerView;
    }

    final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if(isRunning) {
                milli++;
                if(milli == 1000){
                    milli = 0;
                    sec++;
                }
                if(sec == 60)
                {
                    sec = 0;
                    min++;
                }
                timerView.setText(formatToMinSecMilli());
            }
        }
    };
    private String formatToMinSecMilli()
    {
        String strMin,strSec,strMilli;
        if(min<10)
            strMin = "0"+String.valueOf(min);
        else
            strMin = String.valueOf(min);

        if(sec<10)
            strSec = "0"+String.valueOf(sec);
        else
            strSec = String.valueOf(sec);

        if(milli<10)
            strMilli = "00"+String.valueOf(milli);
        else if(milli>=10 && milli<100)
            strMilli = "0"+String.valueOf(milli);
        else
            strMilli = String.valueOf(milli);

        return strMin+":"+strSec+":"+strMilli.substring(0,2);
    }

    public void initiateTimer()
    {
        isRunning = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdateTimer();
            }
        },0,1);
    }
    public String stopTimerReturnDuration()
    {
        isRunning = false;
        return formatToMinSecMilli();
    }

    private void UpdateTimer()
    {
        myHandler.post(timerRunnable);
    }

}
