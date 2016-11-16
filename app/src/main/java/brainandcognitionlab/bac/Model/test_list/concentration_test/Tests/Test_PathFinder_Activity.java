package brainandcognitionlab.bac.Model.test_list.concentration_test.Tests;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import brainandcognitionlab.bac.Controller.Timer.Chronometer;
import brainandcognitionlab.bac.Controller.ConstructPathInMatrix;
import brainandcognitionlab.bac.Controller.DB.DBHelper;
import brainandcognitionlab.bac.Controller.DB.Date;
import brainandcognitionlab.bac.Controller.DB.Game;
import brainandcognitionlab.bac.Controller.Line;
import brainandcognitionlab.bac.Controller.DB.Record;
import brainandcognitionlab.bac.Controller.RoundController.RoundStatus;
import brainandcognitionlab.bac.Controller.DB.User;
import brainandcognitionlab.bac.Model.AlertFunctions;
import brainandcognitionlab.bac.Model.TableFunctions;
import brainandcognitionlab.bac.R;

public class Test_PathFinder_Activity extends Activity
        implements View.OnTouchListener {

    private TableLayout table_layout;
    private int numOfTurns = 5;
    private ConstructPathInMatrix constructPathInMatrix;
    private int interval = 1000;
    private int ROWS = 10;
    private int COLS = 10;
    private RoundStatus rs;
    //라운드는 별표가 몇개 맞았는지, 레벨은 게임 난이도를 명시한다.
    private ImageView stars[] = new ImageView[3];
    private int level = 1;
    private int numOfStar = 0;
    private ImageButton table[][] = new ImageButton[ROWS][];
    private TextView tempStatusView;
    private TableFunctions tableFunc = new TableFunctions(this);
    private AlertFunctions alertFunc = new AlertFunctions(this);
    private Chronometer chronometer;
    private Record record;
    private Game game;
    private User user;
    private DBHelper dbHelper = new DBHelper(this);
    private String start_date, end_date;
    private int lengthOfPath = 0; // this variable is used for setting tag of table block
    private int numOfClicks = 0; // this variable keeps track of the path
    private int currentTag = -1;
    private int symmetricNumber; // randomize the starting point(9);
    private Rect outRect = new Rect(); // check View boundary
    int [] location = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pathfinder);

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
            numOfTurns = numOfTurns + level - 1;
            numOfStar = rs.getNumOfStars();
            //
            statusOfStars();

        }
        tempStatusView = (TextView) findViewById(R.id.status);
        tempStatusView.setTextSize(20.0f);
        tempStatusView.setText("level   " + rs.getLevel() + " first   "
                + rs.getInternalStage().first + " second   " + rs.getInternalStage().second + " stars   "
                + rs.getNumOfStars());
        //테이블을 생성하는 부분
        table_layout = (TableLayout) findViewById(R.id.tableLayout);

        tableFunc.buildTable(ROWS, COLS, table, table_layout, R.drawable.dot3);
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++) {
                table[i][j].setOnTouchListener(this);
                table[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
            }

        symmetricNumber = Math.random() - 0.5 > 0 ? 9 : 0;

        constructPathInMatrix = new ConstructPathInMatrix(numOfTurns);

        tableFunc.cleanTable(table, ROWS, COLS, R.drawable.dot3);

        BlockAsynkTask blockAsynkTask = new BlockAsynkTask();
        blockAsynkTask.execute(constructPathInMatrix.getPath());
    }

    private class BlockAsynkTask extends AsyncTask<Line, Line, Integer> {
        @Override
        protected Integer doInBackground(Line... lines) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < lines.length; i++) {
                publishProgress(lines[i]);
                Log.i("asynktask", String.valueOf(lines.length));
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 0;
        }
        @Override
        protected void onPreExecute() {
            tableFunc.disableClickBlocks(true, table, ROWS, COLS);
        }

        protected void onProgressUpdate(Line... line) {
            Log.i("asynktask", String.valueOf("X " + String.valueOf(line[0].getP1().getX()) + " " + String.valueOf(line[0].getP1().getY())) + " Y " +
                    String.valueOf(line[0].getP2().getX() + " " + String.valueOf(line[0].getP2().getY())));

            if (line[0].isHorizontal()) {
                for (int i = line[0].getP1().getX(); i != line[0].getP2().getX(); ) {
                    table[9 - line[0].getP1().getY()][Math.abs(symmetricNumber - i)].setImageDrawable(getDrawable(R.drawable.reddot2));
                    table[9 - line[0].getP1().getY()][Math.abs(symmetricNumber - i)].setScaleType(ImageView.ScaleType.FIT_XY);
                    table[9 - line[0].getP1().getY()][Math.abs(symmetricNumber - i)].setTag(lengthOfPath++);
                    Log.i("dd", String.valueOf(Math.abs(symmetricNumber - i)));
                    if (line[0].getP1().getX() > line[0].getP2().getX())
                        i--;
                    else
                        i++;
                }
            } else {
                for (int i = line[0].getP1().getY(); i != line[0].getP2().getY(); ) {
                    table[9 - i][Math.abs(symmetricNumber - line[0].getP1().getX())].setImageDrawable(getDrawable(R.drawable.reddot2));
                    table[9 - i][Math.abs(symmetricNumber - line[0].getP1().getX())].setScaleType(ImageView.ScaleType.FIT_XY);
                    table[9 - i][Math.abs(symmetricNumber - line[0].getP1().getX())].setTag(lengthOfPath++);
                    if (line[0].getP1().getY() > line[0].getP2().getY())
                        i--;
                    else
                        i++;
                }
            }

            Log.i("asynktask", "length = " + String.valueOf(Math.abs(symmetricNumber - line[0].getP1().getX())));

            table[9 - line[0].getP1().getY()][Math.abs(symmetricNumber - line[0].getP1().getX())].setImageDrawable(getDrawable(R.drawable.yellow_star2));
            table[9 - line[0].getP1().getY()][Math.abs(symmetricNumber - line[0].getP1().getX())].setScaleType(ImageView.ScaleType.FIT_XY);

            table[9 - line[0].getP2().getY()][Math.abs(symmetricNumber - line[0].getP1().getX())].setImageDrawable(getDrawable(R.drawable.yellow_star2));
            table[9 - line[0].getP2().getY()][Math.abs(symmetricNumber - line[0].getP1().getX())].setScaleType(ImageView.ScaleType.FIT_XY);

        }

        protected void onPostExecute(Integer result) {
            //table[0][0].setTag(lengthOfPath);
            table[0][9 - symmetricNumber].setTag(lengthOfPath);
            table[0][9 - symmetricNumber].setImageDrawable(getDrawable(R.drawable.yellow_star2));
            Date date = new Date();
            start_date = date.getDate();
            Log.i("asynktask", "onPostExecute");
            tableFunc.disableClickBlocks(false, table, ROWS, COLS);
            chronometer = (Chronometer) findViewById(R.id.chronometer);
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            tableFunc.cleanTable(table, ROWS, COLS, R.drawable.dot3);
        }
    }

    public void statusOfStars() {
        for (int i = 0; i < rs.getNumOfStars(); i++) {
            stars[i].setImageDrawable(getResources().getDrawable(R.drawable.yellow_star));
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.i("test", String.valueOf(v.getTag())+"View");

        int x = (int)event.getRawX();
        int y = (int)event.getRawY();

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(isViewInBounds(v,x,y)){
                if ((int) v.getTag() == numOfClicks && currentTag != (int) v.getTag()) {
                    v.setClickable(false);
                    numOfClicks++;
                    Log.i("listener", String.valueOf(currentTag) + " " + String.valueOf((int) v.getTag()));
                    currentTag = (int) v.getTag();
                    tableFunc.makeButtonCorrectImage((ImageButton) v, R.drawable.reddot2);
                    if (numOfClicks == lengthOfPath + 1) {
                        rs.successToRound();
                        statusOfStars();
                        if (rs.getNumOfStars() == 0)
                            stars[2].setImageDrawable(getResources().getDrawable(R.drawable.yellow_star));
                        chronometer.stop();
                        Date date = new Date();
                        end_date = date.getDate();
                        record = new Record(user, (String) chronometer.getText(), String.valueOf(rs.getLevel())
                                , true, DBHelper.getSingleton_DB_Instance(this).getGameId(game), start_date, end_date);
                        Intent intent = new Intent(this, Test_PathFinder_Activity.class);
                        intent.putExtra("user", user);
                        intent.putExtra("record", record);
                        intent.putExtra("game", game);
                        if (rs.isFinished()) {
                            finish();
                        } else {
                            intent.putExtra("rs", rs);
                            alertFunc.alertSuccessDialogWithTransition(intent);
                        }
                    }
                } else if (currentTag == (int)v.getTag() || (int)v.getTag() < currentTag) {
                } else {
                    rs.failToRound();
                    chronometer.stop();
                    Date date = new Date();
                    end_date = date.getDate();
                    record = new Record(user, (String) chronometer.getText(), String.valueOf(rs.getLevel())
                            , false, DBHelper.getSingleton_DB_Instance(this).getGameId(game), start_date, end_date);
                    Intent intent = new Intent(this, Test_PathFinder_Activity.class);
                    intent.putExtra("user", user);
                    intent.putExtra("record", record);
                    intent.putExtra("game", game);
                    intent.putExtra("rs", rs);
                    alertFunc.alertFailDialogWithTransition(intent);
                }
            }else{
                for(int i=0;i<ROWS;i++){
                    for(int j=0;j<COLS;j++){
                        if(isViewInBounds(table[i][j],x,y)){
                            table[i][j].dispatchTouchEvent(event);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
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
        }).show();
    }

    private boolean isViewInBounds(View view, int x, int y){
        view.getDrawingRect(outRect);
        view.getLocationOnScreen(location);
        outRect.offset(location[0],location[1]);
        return outRect.contains(x,y);
    }

}