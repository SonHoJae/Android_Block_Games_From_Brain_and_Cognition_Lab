

package brainandcognitionlab.bac.Model.test_list.concentration_test.Tests;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import brainandcognitionlab.bac.Controller.DB.DBHelper;
import brainandcognitionlab.bac.Controller.DB.Date;
import brainandcognitionlab.bac.Controller.DB.Game;
import brainandcognitionlab.bac.Controller.DB.Record;
import brainandcognitionlab.bac.Controller.DB.User;
import brainandcognitionlab.bac.Controller.RoundController.RoundStatus;
import brainandcognitionlab.bac.Controller.Timer.Chronometer;
import brainandcognitionlab.bac.Model.AlertFunctions;
import brainandcognitionlab.bac.Model.StatusLayout;
import brainandcognitionlab.bac.R;

public class Test_CountingBananas_Activity extends AppCompatActivity implements View.OnClickListener {

    private int level = 1;
    private Animation rotation;
    final private EditText answerEdt[] = new EditText[2];
    final private Monkey monkeys[] = new Monkey[4];
    private RoundStatus rs;
    private ImageView stars[];
    private StatusLayout statusLayout;
    private DBHelper dbHelper = new DBHelper(this);
    private TextView tempStatusView;
    private Chronometer chronometer;
    private AlertFunctions alertFunc = new AlertFunctions(this);
    private Record record;
    private Game game;
    private User user;
    private int numOfStar = 0;
    private String start_date, end_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_counting_bananas);
        Intent intent = getIntent();
        game = intent.getExtras().getParcelable("game");
        user = intent.getExtras().getParcelable("user");
        //timerView = (TextView)findViewById(R.id.timerview);
        //timerView.setTextSize(30.0f);
        stars = new ImageView[3];
        stars[0] = (ImageView) findViewById(R.id.first_star);
        stars[1] = (ImageView) findViewById(R.id.second_star);
        stars[2] = (ImageView) findViewById(R.id.third_star);
        tempStatusView = (TextView) findViewById(R.id.status);
        statusLayout = new StatusLayout(this, stars, tempStatusView);
        Bundle bundle = intent.getExtras();

        if (bundle.getParcelable("rs") == null) {
            rs = new RoundStatus(1, new Pair(0, 0), 0, false);
        } else// 첫번째 게임이 아닌 이전 정보를 받아와야한다면,
        {
            rs = intent.getExtras().getParcelable("rs");
            level = rs.getLevel();
            numOfStar = rs.getNumOfStars();
            statusLayout.setStars(rs);
        }
        statusLayout.setStatusView(tempStatusView, rs);
        monkeys[0] = new Monkey(0, this);
        monkeys[1] = new Monkey(1, this);
        monkeys[2] = new Monkey(2, this);
        monkeys[3] = new Monkey(3, this);
        rotation = AnimationUtils.loadAnimation(this, R.anim.throw_banana_animation);
        monkeys[0].setMonkeyImageView(R.id.monkeyImage1);
        monkeys[1].setMonkeyImageView(R.id.monkeyImage2);
        monkeys[2].setMonkeyImageView(R.id.monkeyImage3);
        monkeys[3].setMonkeyImageView(R.id.monkeyImage4);
        //rotation.setFillBefore(true);

        answerEdt[0] = (EditText) findViewById(R.id.answerEdt1);
        answerEdt[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditorTxtClick((EditText)v);
            }
        });
        answerEdt[1] = (EditText) findViewById(R.id.answerEdt2);
        answerEdt[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditorTxtClick((EditText)v);
            }
        });
        ThrowingBananasAsynkTask throwingBananasAsynkTask = new ThrowingBananasAsynkTask();
        throwingBananasAsynkTask.execute(monkeys);

        Button submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(this);
    }

    private class ThrowingBananasAsynkTask extends AsyncTask<Monkey[], Monkey[], Integer> {
        @Override
        protected Integer doInBackground(Monkey[]... monkeys) {

            for (int i = 0; i < level * 2; i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(monkeys);
                Log.i("test", "test");
            }
            return 1;
        }

        protected void onProgressUpdate(Monkey[]... monkeys) {

            Log.i("test", "test2");
            bananaEvent(monkeys[0]);
        }

        @Override
        protected void onPostExecute(Integer test) {
            Log.i("test", "banan" + String.valueOf(monkeys[0].getNumOfBananas()) + String.valueOf(monkeys[1].getNumOfBananas()));
            Date date = new Date();
            start_date = date.getDate();
            chronometer = (Chronometer) findViewById(R.id.chronometer);
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        }
    }

    public void onEditorTxtClick(EditText v){
        v.setText("");
    }

    @Override
    public void onClick(View v) {
        if(String.valueOf(monkeys[0].getNumOfBananas()).equals(answerEdt[0].getText().toString())
                && String.valueOf(monkeys[1].getNumOfBananas()).equals(answerEdt[1].getText().toString())){
            rs.successToRound();
            if (rs.getNumOfStars() == 0)
                statusLayout.setStarYellow(stars[2]);
//                    timerHandler.stopTimerReturnDuration();
            //TODO: UserId and gamdID from Databse
            chronometer.stop();
            Date date = new Date();
            end_date = date.getDate();
            Log.i("date_info","start_date = "+start_date+" end_date = "+end_date);
            record = new Record(user,(String) chronometer.getText(), String.valueOf(rs.getLevel())
                    , true, DBHelper.getSingleton_DB_Instance(this).getGameId(game),start_date,end_date);
            Intent intent = new Intent(this, Test_CountingBananas_Activity.class);
            intent.putExtra("user",user);
            intent.putExtra("record", record);
            intent.putExtra("game",game);
            if (rs.isFinished()) {
                finish();
            } else {
                intent.putExtra("rs", rs);
                alertFunc.alertSuccessDialogWithTransition(intent);
            }
        }else{
            rs.failToRound();
            chronometer.stop();
            Date date = new Date();
            end_date = date.getDate();
            Log.i("date_info","start_date = "+start_date+" end_date = "+end_date);
            record = new Record(user,(String) chronometer.getText(), String.valueOf(rs.getLevel())
                    , false , DBHelper.getSingleton_DB_Instance(this).getGameId(game),start_date,end_date);
            Intent intent = new Intent(this, Test_CountingBananas_Activity.class);
            intent.putExtra("user",user);
            intent.putExtra("record", record);
            intent.putExtra("game",game);
            intent.putExtra("rs", rs);
            alertFunc.alertFailDialogWithTransition(intent);
        }
    }
    public void bananaEvent(Monkey monkeys[]){
        boolean randomBoolean = new Random().nextBoolean();
        boolean randomBoolean2 = new Random().nextBoolean();
        if(randomBoolean){
            if(randomBoolean2){
                Log.i("test","0");
                monkeys[0].getABanana();
                monkeys[0].startAnimation();
            }else{
                Log.i("test","1");
                monkeys[1].getABanana();
                monkeys[1].startAnimation();
            }
        }else{
            if(randomBoolean2){
                Log.i("test","0");
                monkeys[0].stealABanana();
                monkeys[2].startAnimation();
            }else{
                Log.i("test","1");
                monkeys[1].stealABanana();
                monkeys[3].startAnimation();
            }
        }
    }

    private class Monkey{
        private int id;
        private int numOfBananas;
        private ImageView monkeyImageView;

        public Monkey(int id, Context context){
            this.id = id;
            numOfBananas = 10;
            monkeyImageView = new ImageView(context);
        }

        public void setMonkeyImageView(int imageId) {
            this.monkeyImageView = (ImageView)findViewById(imageId);
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNumOfBananas() {
            return numOfBananas;
        }

        public void setNumOfBananas(int numOfBananas) {
            this.numOfBananas = numOfBananas;
        }

        public void stealABanana()
        {
            if(this.getNumOfBananas() > 0){
                this.setNumOfBananas(this.getNumOfBananas() - 1);
            }
        }

        public void getABanana(){
            numOfBananas++;
        }

        public void startAnimation(){
            this.monkeyImageView.setDrawingCacheEnabled(true);
            this.monkeyImageView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
            this.monkeyImageView.startAnimation(rotation);
        }
    }
    @Override
    public void onBackPressed() {
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
                        finish();
                    }
                }).start();
            }
        });
        alertDialog.setPositiveButton("테스트 종료",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }
}
