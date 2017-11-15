package brainandcognitionlab.bac.Model.test_list.concentration_test.Tests;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import brainandcognitionlab.bac.Controller.Timer.Chronometer;
import brainandcognitionlab.bac.Controller.DB.DBHelper;
import brainandcognitionlab.bac.Controller.DB.Date;
import brainandcognitionlab.bac.Controller.DB.Game;
import brainandcognitionlab.bac.Controller.DB.Record;
import brainandcognitionlab.bac.Controller.RoundController.RoundStatus;
import brainandcognitionlab.bac.Controller.DB.User;
import brainandcognitionlab.bac.Model.AlertFunctions;
import brainandcognitionlab.bac.Model.StatusLayout;
import brainandcognitionlab.bac.Model.TableFunctions;
import brainandcognitionlab.bac.R;

public class Test_OrderTracker_Activity extends Activity implements OnClickListener {

    private TableLayout table_layout;
    private int pickedSize = 2;
    private int interval = 1000;
    private int pickedBlockNumbers[];
    private int ROWS = 5;
    private int COLS = 5;
    private RoundStatus rs;
    private int orderCheckNumber = 0;
    //라운드는 별표가 몇개 맞았는지, 레벨은 게임 난이도를 명시한다.
    private ImageView stars[];
    private int level = 1;
    private int numOfStar = 0;
    private ImageButton table[][];//= new Button[ROWS][];
    private TextView tempStatusView;
    private TableFunctions tableFunc = new TableFunctions(this);
    private AlertFunctions alertFunc = new AlertFunctions(this);
    private Record record;
    private Game game;
    private User user;
    private StatusLayout statusLayout;
    private Chronometer chronometer;
    private DBHelper dbHelper = new DBHelper(this);
    private String start_date,end_date;
    private Integer candidateImageId[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_order_tracker);

        Intent intent = getIntent();
        game = intent.getExtras().getParcelable("game");
        user = intent.getExtras().getParcelable("user");
        stars = new ImageView[3];
        stars[0] = (ImageView) findViewById(R.id.first_star);
        stars[1] = (ImageView) findViewById(R.id.second_star);
        stars[2] = (ImageView)findViewById(R.id.third_star);
        Bundle bundle = intent.getExtras();
        if(bundle.getParcelable("rs") == null)
        {
            rs = new RoundStatus(1,new Pair(0,0),0,false);
        }
        else// 첫번째 게임이 아닌 이전 정보를 받아와야한다면,
        {
            //
            rs = intent.getExtras().getParcelable("rs");
            level = rs.getLevel();
            pickedSize = level+1;
            numOfStar = rs.getNumOfStars();
            //
            statusOfStars();

        }
        tempStatusView = (TextView)findViewById(R.id.status);
        tempStatusView.setTextSize(20.0f);
        tempStatusView.setText("level   " + rs.getLevel() + " first   "
                + rs.getInternalStage().first + " second   " + rs.getInternalStage().second + " stars   "
                + rs.getNumOfStars());

        candidateImageId = new Integer[9];
        for(int i=0;i<9;i++) {
            candidateImageId[i] = this.getResources().getIdentifier("s"+String.valueOf(i), "drawable", this.getPackageName());
        }

        //테이블을 생성하는 부분
        table = new ImageButton[ROWS][];
        table_layout = (TableLayout) findViewById(R.id.tableLayout);
        tableFunc.buildTable(ROWS, COLS, table, table_layout,R.drawable.s);
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++) {
                table[i][j].setOnClickListener(this);
            }
        pickedBlockNumbers = tableFunc.pickRandomBlocks(pickedSize);
        ArrayList<Integer> arraylist = new ArrayList<Integer>();
        for (int i = 0; i < pickedSize; i++) {
            arraylist.add(pickedBlockNumbers[i]);
        }

        tableFunc.cleanTable(table,ROWS,COLS,R.drawable.s);

        BlockAsynkTask blockAsynkTask = new BlockAsynkTask();
        blockAsynkTask.execute(arraylist);
    }

    private class BlockAsynkTask extends AsyncTask<ArrayList<Integer>, Integer, Integer> {
        @Override
        protected Integer doInBackground(ArrayList<Integer>[] params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < pickedSize; i++) {//table[params[0].get(i)/5][params[0].get(i)%5];
                publishProgress(params[0].get(i));
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("test", String.valueOf(params[0].get(i)));
            }
            return 0;
        }
        @Override
        protected void onPreExecute() {
            tableFunc.disableClickBlocks(true, table, ROWS, COLS);
        }

        protected void onProgressUpdate(Integer... result) {
            Log.i("test", String.valueOf(result) + "dd");

            tableFunc.cleanTable(table,ROWS,COLS,R.drawable.s);

            table[result[0] / ROWS][result[0] % COLS].setImageResource(candidateImageId[1]);
            table[result[0] / ROWS][result[0] % COLS].setScaleType(ImageView.ScaleType.FIT_XY);
            table[result[0] / ROWS][result[0] % COLS].setAdjustViewBounds(true);
            //table[result[0] / ROWS][result[0] % COLS].setText("T");

            //table[result[0]/pickedSize][result[0]%pickedSize].setBackgroundColor(Color.RED);
        }

        protected void onPostExecute(Integer result) {
            Date date = new Date();
            start_date = date.getDate();
            tableFunc.cleanTable(table,ROWS,COLS,R.drawable.s);
            tableFunc.disableClickBlocks(false, table, ROWS, COLS);
            chronometer = (Chronometer) findViewById(R.id.chronometer);
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        }
    }

    public void statusOfStars() {
        for (int i = 0; i < rs.getNumOfStars() ; i++) {
            stars[i].setImageDrawable(getResources().getDrawable(R.drawable.yellow_star));
        }
    }

    @Override
    public void onClick(View v) {

        Log.i("test", v.getTag() + " " + pickedBlockNumbers[orderCheckNumber]);
        if ((int)v.getTag() == pickedBlockNumbers[orderCheckNumber]) {
            //table[(int) v.getTag() / ROWS][(int) v.getTag() % COLS].setText("T");
            table[(int) v.getTag() / ROWS][(int) v.getTag() % COLS].setClickable(false);
            orderCheckNumber++;
            tableFunc.makeButtonCorrectImage((ImageButton)v,R.drawable.s1);
            if (orderCheckNumber == pickedSize) {
                rs.successToRound();
                statusOfStars();
                if(rs.getNumOfStars() == 0)
                    stars[2].setImageDrawable(getResources().getDrawable(R.drawable.yellow_star));
                chronometer.stop();
                Date date = new Date();
                end_date = date.getDate();
                record = new Record(user,(String) chronometer.getText(), String.valueOf(rs.getLevel())
                        , true , DBHelper.getSingleton_DB_Instance(this).getGameId(game),start_date,end_date);
                Intent intent = new Intent(this, Test_OrderTracker_Activity.class);
                intent.putExtra("user",user);
                intent.putExtra("record", record);
                intent.putExtra("game",game);
                if (rs.isFinished()) {
                    finish();
                } else {
                    intent.putExtra("rs", rs);
                    alertFunc.alertSuccessDialogWithTransition(intent);
                }
            }
        } else {
            rs.failToRound();
            chronometer.stop();
            Date date = new Date();
            end_date = date.getDate();
            record = new Record(user,(String) chronometer.getText(), String.valueOf(rs.getLevel())
                    , false , DBHelper.getSingleton_DB_Instance(this).getGameId(game),start_date,end_date);
            Intent intent = new Intent(this, Test_OrderTracker_Activity.class);
            intent.putExtra("user",user);
            intent.putExtra("record", record);
            intent.putExtra("game",game);
            intent.putExtra("rs", rs);
            alertFunc.alertFailDialogWithTransition(intent);
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
        alertDialog.setPositiveButton("테스트 종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }
}

