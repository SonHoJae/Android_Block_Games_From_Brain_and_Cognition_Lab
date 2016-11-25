package brainandcognitionlab.bac.Model.test_list.concentration_test.Tests;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import brainandcognitionlab.bac.Controller.Timer.Chronometer;
import brainandcognitionlab.bac.Controller.DB.DBHelper;
import brainandcognitionlab.bac.Controller.DB.Date;
import brainandcognitionlab.bac.Controller.DB.Game;
import brainandcognitionlab.bac.Controller.DB.Record;
import brainandcognitionlab.bac.Controller.RoundController.RoundStatus;
import brainandcognitionlab.bac.Controller.Shuffle;
import brainandcognitionlab.bac.Controller.DB.User;
import brainandcognitionlab.bac.Model.AlertFunctions;
import brainandcognitionlab.bac.Model.StatusLayout;
import brainandcognitionlab.bac.Model.TableFunctions;
import brainandcognitionlab.bac.R;

public class Test_UserChoosenBlockTracker_Activity extends Activity implements OnClickListener {

    private TableLayout table_layout;
    private int pickedSize = 2;
    private int []pickedRandomBlocks;
    private int []pickRandomValues;
    BlockAsynkTask blockAsynkTask;
    ArrayList<Integer> blockPositionArrayList;
    ArrayList<Integer> randomValueArrayList;
    private int ROWS = 5;
    private int COLS = 5;
    private RoundStatus rs;
    private int currentPoint = 0;
    private int []trackedMemory;
    private ImageView stars[];
    private int level = 1;
    private int numOfStar = 0;
    private String selectCandidateNumber;
    private ImageButton table[][];
    private ImageButton pic[] = new ImageButton[10];
    private TextView tempStatusView;
    private Chronometer chronometer;
    private TableFunctions tableFunc = new TableFunctions(this);
    private AlertFunctions alertFunc = new AlertFunctions(this);
    private Record record;
    private Game game;
    private User user;
    private StatusLayout statusLayout;
    private DBHelper dbHelper = new DBHelper(this);
    private String start_date,end_date;
    private Integer candidateImageId[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_user_choosen_block_tracker);

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
        statusLayout = new StatusLayout(this,stars,tempStatusView);
        Bundle bundle = intent.getExtras();

        if (bundle.getParcelable("rs") == null) {
            rs = new RoundStatus(1, new Pair(0, 0), 0, false);
        } else// 첫번째 게임이 아닌 이전 정보를 받아와야한다면,
        {
            rs = intent.getExtras().getParcelable("rs");
            level = rs.getLevel();
            pickedSize = level + 1;
            numOfStar = rs.getNumOfStars();
            statusLayout.setStars(rs);
        }
        statusLayout.setStatusView(tempStatusView,rs);
        //////////////////candidate btn///////////////////
        Shuffle shuffle = new Shuffle();
        int indexArray [] = shuffle.shuffleIndexArrayFromPool(10,26); 
        candidateImageId = new Integer[10];
        for(int i=0;i<10;i++) {
            candidateImageId[i] = this.getResources().getIdentifier("i"+String.valueOf(indexArray[i]), "drawable", this.getPackageName());
        }

        //BuildCandidateLayout();

        //The array trackedMemory is the space tracking user's clicking record.
        trackedMemory = new int[pickedSize];
        for(int i=0; i<pickedSize; i++)
            trackedMemory[i] = -2;

        //테이블을 생성하는 부분 -> 이 위에 initialize module만들어야함.
        table = new ImageButton[ROWS][];
        table_layout = (TableLayout) findViewById(R.id.tableLayout);

        tableFunc.buildTable(ROWS, COLS, table, table_layout,R.drawable.s);
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++) {
                table[i][j].setOnClickListener(this);
                table[i][j].setTag(-1);
            }
        pickedRandomBlocks = tableFunc.pickRandomBlocks(10);
        blockPositionArrayList = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            blockPositionArrayList.add(pickedRandomBlocks[i]);
        }

        pickRandomValues = tableFunc.pickRandomNumbers(10);
        randomValueArrayList = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            randomValueArrayList.add(pickRandomValues[i]);
        }
        tableFunc.cleanTable(table,ROWS,COLS,R.drawable.s);

        blockAsynkTask = new BlockAsynkTask();
        blockAsynkTask.execute(blockPositionArrayList,randomValueArrayList);
    }


    private void BuildCandidateLayout() {
        final LinearLayout candidateLayout1 = (LinearLayout) findViewById(R.id.candidateLayout1);
        LinearLayout candidateLayout2 = (LinearLayout) findViewById(R.id.candidateLayout2);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
        lp.weight = 0.2f;

        for (int i = 0; i < 10; i++) {

            pic[i] = new ImageButton(this);
            pic[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
            pic[i].setAdjustViewBounds(true);
            pic[i].setTag(String.valueOf(i));
            pic[i].setScaleType(ImageView.ScaleType.FIT_XY);
            pic[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    candidateListener(v);
                }
            });

        }
        for(int i=0; i<10; i++)
            pic[i].setImageResource(candidateImageId[i]);

        Shuffle shuffle = new Shuffle();
        ImageButton[] shuffledPic = shuffle.shuffleArray(pic);

        for (int i = 0; i < 10; i++) {
            if (i < 5)
                candidateLayout1.addView(shuffledPic[i], lp);
            else
                candidateLayout2.addView(shuffledPic[i], lp);
        }
    }

    public void candidateListener(View v) {
        cleanColorBlocks();
        v.setBackgroundColor(Color.DKGRAY);
        selectCandidateNumber = (String) v.getTag();
    }

    public void cleanColorBlocks() {
        for (int i = 0; i < 10; i++) {
            pic[i].setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }

    private class BlockAsynkTask extends AsyncTask<ArrayList<Integer>, Integer, Integer> {
        @Override
        protected Integer doInBackground(ArrayList<Integer>... params) {
            try {
                Thread.sleep(3000);
                publishProgress(-2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 10; i++)
                publishProgress(params[0].get(i), params[1].get(i));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0;
        }

        @Override
        protected void onPreExecute() {
            tableFunc.disableClickBlocks(true,table,ROWS,COLS);
            //tableFunc.cleanTable(table,ROWS,COLS,R.drawable.i);
        }

        protected void onProgressUpdate(Integer... result) {

            if(result[0] == -2){
                tableFunc.cleanTable(table,ROWS,COLS,R.drawable.i);
            }else {
                table[result[0] / ROWS][result[0] % COLS].setTag(String.valueOf(result[1]));
                table[result[0] / ROWS][result[0] % COLS].setImageResource(candidateImageId[result[1]]);
                table[result[0] / ROWS][result[0] % COLS].setTag(R.string.candidate, candidateImageId[result[1]]);
                table[result[0] / ROWS][result[0] % COLS].setScaleType(ImageView.ScaleType.FIT_XY);
            }

        }

        protected void onPostExecute(Integer result) {
            tableFunc.cleanTable(table,ROWS,COLS,R.drawable.w_background);
            Date date = new Date();
            start_date = date.getDate();
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
        ImageButton clickedTableBlock = (ImageButton) v;
        clickedTableBlock.setClickable(false);
        Log.i("endOfTest", String.valueOf(clickedTableBlock.getTag()) + "--a" + String.valueOf(trackedMemory[currentPoint]));
        Log.i("endOfTest", String.valueOf(trackedMemory[currentPoint]));
        Log.i("endOfTest", String.valueOf(clickedTableBlock.getTag()));
        Log.i("endOfTest", String.valueOf(-2 == trackedMemory[currentPoint]));
        Log.i("endOfTest", String.valueOf(-1 ==Integer.parseInt(clickedTableBlock.getTag().toString())));
        if(trackedMemory[currentPoint] == -2 && Integer.parseInt(clickedTableBlock.getTag().toString())!= -1) {
            //successful in small round
            trackedMemory[currentPoint] = Integer.parseInt(String.valueOf(clickedTableBlock.getTag()));

            clickedTableBlock.setBackgroundColor(Color.CYAN);
            clickedTableBlock.setImageResource((int)v.getTag(R.string.candidate));
            currentPoint = 0;

            blockAsynkTask = new BlockAsynkTask();
            pickedRandomBlocks = tableFunc.pickRandomBlocks(10);
            blockPositionArrayList = new ArrayList<Integer>();
            for (int i = 0; i < 10; i++) {
                blockPositionArrayList.add(pickedRandomBlocks[i]);
            }
            Collections.shuffle(randomValueArrayList);
            blockAsynkTask.execute(blockPositionArrayList,randomValueArrayList);
        }else if(trackedMemory[currentPoint] == Integer.parseInt(String.valueOf(clickedTableBlock.getTag()))){
            //following userChoosenBlock
            clickedTableBlock.setBackgroundColor(Color.CYAN);
            clickedTableBlock.setImageResource((int)v.getTag(R.string.candidate));
            if(currentPoint+1 == pickedSize) {
                rs.successToRound();
                statusOfStars();
                if (rs.getNumOfStars() == 0)
                    stars[2].setImageDrawable(getResources().getDrawable(R.drawable.yellow_star));
                chronometer.stop();
                Date date = new Date();
                end_date = date.getDate();
                record = new Record(user,(String) chronometer.getText(), String.valueOf(rs.getLevel())
                        , true , DBHelper.getSingleton_DB_Instance(this).getGameId(game),start_date,end_date);
                Intent intent = new Intent(this, Test_UserChoosenBlockTracker_Activity.class);
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

            currentPoint++;
        }else{
            rs.failToRound();
            chronometer.stop();
            Date date = new Date();
            end_date = date.getDate();
            record = new Record(user,(String) chronometer.getText(), String.valueOf(rs.getLevel())
                    , false , DBHelper.getSingleton_DB_Instance(this).getGameId(game),start_date,end_date);
            Intent intent = new Intent(this, Test_UserChoosenBlockTracker_Activity.class);
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
        alertDialog.setPositiveButton("테스트 종료",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).show();
    }
}

