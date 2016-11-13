package brainandcognitionlab.bac.Model.test_list.concentration_test.Tests;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import brainandcognitionlab.bac.Controller.DB.DBHelper;
import brainandcognitionlab.bac.Controller.DB.Date;
import brainandcognitionlab.bac.Controller.DB.Game;
import brainandcognitionlab.bac.Controller.DB.Record;
import brainandcognitionlab.bac.Controller.DB.User;
import brainandcognitionlab.bac.Controller.RoundController.RoundStatus;
import brainandcognitionlab.bac.Controller.Shuffle;
import brainandcognitionlab.bac.Controller.Timer.Chronometer;
import brainandcognitionlab.bac.Model.AlertFunctions;
import brainandcognitionlab.bac.R;

public class Test_JudgeFromStimulus_OX_Activity extends Activity implements OnClickListener {

    private int interval = 1000;
    private long loadingTime = 2000;
    private RoundStatus rs;
    private int orderCheckNumber = 0;
    private int stimulusArray[];
    private ImageView stimulusView;
    private Button judgeBtn;
    private ImageView stars[] = new ImageView[3];
    private int level = 1;
    private final int normalStimulusSize = 30;
    private final int advancedStimulusSize = 40;
    private int numOfStar = 0;
    private TextView tempStatusView;
    private AlertFunctions alertFunc;
    private Chronometer chronometer;
    private Record record;
    private Game game;
    private User user;
    private DBHelper dbHelper = new DBHelper(this);
    private String start_date, end_date;
    private BlockAsynkTask blockAsynkTask;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_test_judge_from_stimulus);

            Intent intent = getIntent();
            game = intent.getExtras().getParcelable("game");
            user = intent.getExtras().getParcelable("user");
            stars[0] = (ImageView) findViewById(R.id.first_star);
            stars[1] = (ImageView) findViewById(R.id.second_star);
            stars[2] = (ImageView) findViewById(R.id.third_star);
            Bundle bundle = intent.getExtras();
            if (bundle.getParcelable("rs") == null) {
                rs = new RoundStatus(1, new Pair(0, 0), 0, false);
            } else// 첫번째 게임이 아닌 이전 정보를 받아와야한다면,
            {
                //
                rs = intent.getExtras().getParcelable("rs");
                level = rs.getLevel();
                numOfStar = rs.getNumOfStars();
                statusOfStars();
            }

            Date date = new Date();
            start_date = date.getDate();
            chronometer = (Chronometer) findViewById(R.id.chronometer);
            chronometer.setBase(SystemClock.elapsedRealtime());
            Thread timerThread = new Thread(new Runnable() {
                public void run() {
                    chronometer.start();
                }
            });
            timerThread.run();

            loadingTime = 2000 - (level-1)*200;
            stimulusView = (ImageView) findViewById(R.id.stimulus);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(200, 200);
//width and height of your Image ,if it is inside Relative change the LinearLayout to RelativeLayout.
            stimulusView.setLayoutParams(layoutParams);
            if (level <= 3) {
                stimulusArray = new int[normalStimulusSize];
            } else {
                stimulusArray = new int[advancedStimulusSize];
            }
            initiateStimulusArray(stimulusArray);

            tempStatusView = (TextView) findViewById(R.id.status);
            tempStatusView.setTextSize(20.0f);
            tempStatusView.setText("level   " + rs.getLevel() + " first   "
                    + rs.getInternalStage().first + " second   " + rs.getInternalStage().second + " stars   "
                    + rs.getNumOfStars());

            stimulusView.setTag(-1);
            stimulusView.setOnClickListener(this);
            blockAsynkTask = new BlockAsynkTask(this);
            blockAsynkTask.execute(stimulusArray);

    }


    private class BlockAsynkTask extends AsyncTask<int[], Integer, Void> {
        private Context context;
        public BlockAsynkTask (Context context){
            this.context = context;
        }
        @Override
        protected Void doInBackground(int[]... stimulusArray) {

            publishProgress(3);
            try {
                Thread.sleep(loadingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 2 * stimulusArray[0].length; i++) {
                if (i % 2 == 0) {
                    publishProgress(stimulusArray[0][i / 2]);
                    if (this.isCancelled()) {
                        return null;
                    }
                } else {
                    publishProgress(-1);
                    if (this.isCancelled()) {
                        return null;
                    }
                }
                try {
                    Thread.sleep(loadingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            publishProgress(2);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... stimulus) {
            Log.i("stimulus",String.valueOf(stimulus[0]));
            stimulusView.setVisibility(View.VISIBLE);
            if (stimulus[0] == 3){
                stimulusView.setVisibility(View.INVISIBLE);
            } else if (stimulus[0] == -1) {
                stimulusView.setEnabled(false);
                stimulusView.setBackgroundColor(Color.alpha(1));
                stimulusView.setImageResource(R.drawable.i);
                if ((int) stimulusView.getTag() == -1)
                    failATest((Activity)this.context);
            } else if(stimulus[0] == 2){
                passATest(this.context,stimulusView);
            } else{
                stimulusView.setEnabled(true);
                if (stimulus[0] == 1) {
                    stimulusView.setImageResource(R.drawable.correct);
                    stimulusView.setTag(-1);
                } else {
                    stimulusView.setImageResource(R.drawable.incorrect);
                    stimulusView.setTag(0);
                }
            }

            Log.i("alpha",String.valueOf(stimulusView.getImageAlpha()));
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }

    public void statusOfStars() {
        for (int i = 0; i < rs.getNumOfStars(); i++) {
            stars[i].setImageDrawable(getResources().getDrawable(R.drawable.yellow_star));
        }
    }


    private int[] initiateStimulusArray(int[] stimulusArray) {
        Integer[] stimulusIntegerArray = new Integer[stimulusArray.length];
        double ratio = 0.50 - level * 0.05;
        for (int i = 0; i < stimulusArray.length; i++) {
            if (ratio * stimulusArray.length <= i){
                Log.i("test","ratio"+String.valueOf(ratio *stimulusArray.length)+" "+String.valueOf(i));
                stimulusIntegerArray[i] = 1;
            }
            else {
                stimulusIntegerArray[i] = 0;
                Log.i("test","ratio"+String.valueOf(ratio *stimulusArray.length)+" "+String.valueOf(i));
            }
        }
        stimulusIntegerArray = new Shuffle().shuffleArray(stimulusIntegerArray);
        int a = 0;
        for (int i = 0; i < stimulusArray.length; i++) {
            stimulusArray[i] = stimulusIntegerArray[i].intValue();
        }
        return stimulusArray;
    }

    @Override
    public void onClick(View v) {

        stimulusView.setEnabled(false);
        stimulusView.setBackgroundColor(Color.CYAN);
        Log.i("test", String.valueOf(v.getTag()));
        if (v.getTag() == -1) {
            v.setTag(0);
        } else {
            failATest(this);
        }
    }

    public void passATest(Context context, View v) {
        blockAsynkTask.cancel(true);
        Log.i("test", "ok");
        v.setTag(0);
        rs.successToRound();
        statusOfStars();
        if (rs.getNumOfStars() == 0)
            stars[2].setImageDrawable(getResources().getDrawable(R.drawable.yellow_star));
        chronometer.stop();
        Date date = new Date();
        end_date = date.getDate();
        record = new Record(user, (String) chronometer.getText(), String.valueOf(rs.getLevel())
                , true, DBHelper.getSingleton_DB_Instance((Activity)context).getGameId(game), start_date, end_date);
        Intent intent = new Intent((Activity)context, Test_JudgeFromStimulus_OX_Activity.class);
        intent.putExtra("user", user);
        intent.putExtra("record", record);
        intent.putExtra("game", game);
        if (rs.isFinished()) {
            finish();
        } else {
            intent.putExtra("rs", rs);
            alertFunc = new AlertFunctions(context);
            alertFunc.alertSuccessDialogWithTransition(intent);
        }
    }

    public void failATest(Context context) {
        blockAsynkTask.cancel(true);
        Log.i("test", "not ok");
        rs.failToRound();
        chronometer.stop();
        Date date = new Date();
        end_date = date.getDate();
        record = new Record(user, (String) chronometer.getText(), String.valueOf(rs.getLevel())
                , false, DBHelper.getSingleton_DB_Instance((Activity)context).getGameId(game), start_date, end_date);
        Intent intent = new Intent((Activity)context, Test_JudgeFromStimulus_OX_Activity.class);
        intent.putExtra("user", user);
        intent.putExtra("record", record);
        intent.putExtra("game", game);
        intent.putExtra("rs", rs);
        alertFunc = new AlertFunctions(context);
        alertFunc.alertFailDialogWithTransition(intent);
    }

    @Override
    public void onBackPressed() {
        blockAsynkTask.cancel(true);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setTitle("종료하기전에 Excel 추출하시겠습니까?");
        alertDialog.setNegativeButton("Excel 추출", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            dbHelper.exportDB();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                finish();
            }
        });
        alertDialog.setPositiveButton("테스트 종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }
}
