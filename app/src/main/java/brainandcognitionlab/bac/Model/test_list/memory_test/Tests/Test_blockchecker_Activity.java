package brainandcognitionlab.bac.Model.test_list.memory_test.Tests;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.TimerTask;

import brainandcognitionlab.bac.R;

public class Test_blockchecker_Activity extends AppCompatActivity implements OnClickListener  {

    private TableLayout table_layout;
    private int numOfTrueBlock = 0;     // Whenever true value is generated, 'trueBlockCount' will increase.
    private int clickCount = 0;
    int ROWS = 5;
    int COLS = 5;
    private Button table[][];//= new Button[ROWS][];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_checker);
        Bundle extras = getIntent().getExtras();
        if(extras == null)
        {
            Log.i("count", "First Activities");
        }
        else{
            if(extras.getInt("previousResult") == 1) {
                ROWS = extras.getInt("ROWS") + 1;
                COLS = extras.getInt("COLS") + 1;
            }
            else {
                ROWS = extras.getInt("ROWS") - 1;
                COLS = extras.getInt("COLS") - 1;
            }
            Log.i("count", "Next Activities" + String.valueOf(ROWS) + " " + String.valueOf(COLS));
        }
        table = new Button[ROWS][];
        table_layout = (TableLayout) findViewById(R.id.tableLayout);

        BuildTable(ROWS, COLS);

        Handler handler = new Handler();

        handler.postDelayed(new TimerTask() {
            @Override
            public void run() {
                for (int i = 0; i < ROWS; i++)
                    for (int j = 0; j < COLS; j++) {
                        table[i][j].setText("");
                        table[i][j].setEnabled(true);
                    }
            }
        },2000);

    }
        private void BuildTable(int rows, int cols) //table button allocation
        {

            //Every button has same listener
            for(int i=0; i<rows; i++)
                    table[i] = new Button[cols];
            for(int i=0; i<rows; i++)
                for(int j=0; j<cols; j++) {
                    table[i][j] = new Button(this);
                    table[i][j].setOnClickListener(this);
                }

            for(int i =0; i< rows; i++){
                TableRow row = new TableRow(this);
                row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                for(int j=0;j< cols;j++){
                    table[i][j].setTag(new Boolean(getRandomBoolean()));
                    table[i][j].setEnabled(false);
                    if((Boolean)table[i][j].getTag() == true) {
                        numOfTrueBlock++;
                        table[i][j].setText(String.valueOf("O"));
                    }
                    Log.i("count", "Button Boolean Value is " + String.valueOf(table[i][j].getTag()));
                    row.addView(table[i][j]);
                }
                table_layout.addView(row);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean getRandomBoolean()
    {
        return Math.random() < 0.5;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button)v;
        v.setEnabled(false);    //한번 클릭한 버튼은 disabled
        if((Boolean)button.getTag() == true) {
            button.setText("1");
            clickCount++;
            Log.i("count",String.valueOf(clickCount)+" "+String.valueOf(numOfTrueBlock));
            if(clickCount == numOfTrueBlock)
            {
                Toast.makeText(this,"다 맞았으니 난이도 상향 조정",Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(this,Test_blockchecker_Activity.class);
                intent.putExtra("ROWS",ROWS );
                intent.putExtra("COLS",COLS );
                intent.putExtra("previousResult",1);
                startActivity(intent);

            }
        } else {
            button.setText("0");
            finish();
            Intent intent = new Intent(this,Test_blockchecker_Activity.class);
            intent.putExtra("ROWS",ROWS);
            intent.putExtra("COLS", COLS);
            intent.putExtra("previousResult", 0);
            startActivity(intent);
            Toast.makeText(this,"틀렸으니 라운드 종료, 난이도 하향 조정",Toast.LENGTH_SHORT).show();
        }
    }
}
