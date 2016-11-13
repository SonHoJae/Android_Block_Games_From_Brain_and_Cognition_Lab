package brainandcognitionlab.bac.Model.test_list.concentration_test.Tests;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import brainandcognitionlab.bac.Controller.Shuffle;
import brainandcognitionlab.bac.Controller.DB.User;
import brainandcognitionlab.bac.Model.AlertFunctions;
import brainandcognitionlab.bac.Model.StatusLayout;
import brainandcognitionlab.bac.Model.TableFunctions;
import brainandcognitionlab.bac.R;

public class Test_MapNumberAsReverseOrder_Letter_Activity extends Activity {

    private TableLayout table_layout;
    private int pickedSize = 2;
    private int interval = 1000;
    private int[] pickedRandomBlocks;
    private int[] pickRandomValues;
    ArrayList<Integer> blockPositionArrayList;
    ArrayList<Integer> randomValueArrayList;
    private ImageView answerImage;
    private int ROWS = 5;
    private int COLS = 5;
    private RoundStatus rs;
    private int numOfClickedBlock = 0;
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
    private String start_date, end_date;
    private Integer candidateImageId[];
    private ImageView candidateImageView[];
    private Display display;
    private Point displaySize;
    private Context mainContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_map_number_as_reverse_order);

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
            pickedSize = level + 1;
            numOfStar = rs.getNumOfStars();
            statusLayout.setStars(rs);
        }
        statusLayout.setStatusView(tempStatusView, rs);
        Shuffle shuffle = new Shuffle();
        int indexArray [] = shuffle.shuffleIndexArrayFromPool(10,26);
        candidateImageId = new Integer[10];
        for(int i=0;i<10;i++) {
            candidateImageId[i] = this.getResources().getIdentifier("w"+String.valueOf(i), "drawable", this.getPackageName());
        }
        //////////////////candidate btn///////////////////
 
        answerImage = (ImageView) findViewById(R.id.answerImage);

        pickRandomValues = tableFunc.pickRandomNumbers(pickedSize);
        randomValueArrayList = new ArrayList<Integer>();
        for (int i = 0; i < pickedSize; i++) {
            randomValueArrayList.add(pickRandomValues[i]);
        }

        display = getWindowManager().getDefaultDisplay();
        displaySize = new Point();
        display.getSize(displaySize);

        table = new ImageButton[2][];
        table_layout = (TableLayout) findViewById(R.id.tableLayout);
        int tagNumber = 0;
        for (int i = 0; i < 2; i++)
            table[i] = new ImageButton[5];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 5; j++) {
                table[i][j] = new ImageButton(this);
                table[i][j].setTag(tagNumber++);
                table[i][j].setBackgroundColor(Color.TRANSPARENT);
                table[i][j].setImageResource(candidateImageId[5*i+j]);
                table[i][j].setAdjustViewBounds(true);
                table[i][j].setScaleType(ImageView.ScaleType.FIT_XY);
            }



        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 5; j++) {
                table[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBlockTouchedActionPerform(v);
                    }
                });
            }

        PopValueAsynkTask popValueAsynkTask = new PopValueAsynkTask();
        popValueAsynkTask.execute(randomValueArrayList);


    }

    private class PopValueAsynkTask extends AsyncTask<ArrayList<Integer>, Integer, ArrayList<Integer>> {
        @Override
        protected ArrayList<Integer> doInBackground(ArrayList<Integer>... params) {

            for (int i = 0; i < pickedSize; i++) {
                publishProgress(params[0].get(i));
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return params[0];
        }

        protected void onProgressUpdate(Integer... result) {
            Log.i("Value", "Value : " + String.valueOf(result[0]));
            answerImage.setImageResource(candidateImageId[result[0]]);
            answerImage.setAdjustViewBounds(true);
            display.getSize(displaySize);
            int width = displaySize.x;
            int height = displaySize.y;
            answerImage.getLayoutParams().height = width/2;
            answerImage.getLayoutParams().width = height/2;
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> randomValueArrayList) {
            answerImage.setVisibility(View.GONE);
            Integer randomArray[] = new Integer[10];
            for(int i=0; i<10; i++)
                randomArray[i] = i;
            new Shuffle().shuffleArray(randomArray);
            for(int i=0;i<10;i++)
                Log.i("test",String.valueOf(randomArray[i]));
            for (int i = 0; i < 2; i++) {
                TableRow row = new TableRow(mainContext);
                for (int j = 0; j < 5; j++) {
                    row.addView(table[randomArray[i*5+j] / 5][ randomArray[i*5+j] % 5]);
                }
                table_layout.addView(row);
            }
            Date date = new Date();
            start_date = date.getDate();
            chronometer = (Chronometer) findViewById(R.id.chronometer);
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        }
    }
    public void onBlockTouchedActionPerform(View v) {
        ImageButton clickedTableBlock = (ImageButton) v;
        Log.i("endOfTest", clickedTableBlock.getTag() + "--" + selectCandidateNumber);

        // 1) check select the candidate number clicked.
        Log.i("isMatch","selectCandidateNumber = "+selectCandidateNumber+" BlockNumber = "+ clickedTableBlock.getTag());
        // 2) check whether it is matched with the number on table.
        if (clickedTableBlock.getTag() == randomValueArrayList.get(pickedSize-numOfClickedBlock-1)) {
            numOfClickedBlock++;
            tableFunc.makeButtonCorrectImage(clickedTableBlock,0);
            clickedTableBlock.setClickable(false);
            // 3) if all button is matched, success or fail
            if (pickedSize == numOfClickedBlock) {
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
                Intent intent = new Intent(this, Test_MapNumberAsReverseOrder_Letter_Activity.class);
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
            Log.i("date_info","start_date = "+start_date+" end_date = "+end_date);
            record = new Record(user,(String) chronometer.getText(), String.valueOf(rs.getLevel())
                    , false , DBHelper.getSingleton_DB_Instance(this).getGameId(game),start_date,end_date);
            Intent intent = new Intent(this, Test_MapNumberAsReverseOrder_Letter_Activity.class);
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

