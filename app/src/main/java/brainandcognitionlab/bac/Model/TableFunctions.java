package brainandcognitionlab.bac.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import brainandcognitionlab.bac.R;

/**
 * Created by hojaeson on 4/4/16.
 */
public class TableFunctions {
    private Context context;
    public TableFunctions(Context context)
    {
        this.context = context;
    }

    public void makeButtonCorrectImage(ImageButton button,int resourceId)
    {
        //Drawable dr = context.getResources().getDrawable(R.drawable.correct);
        //Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        //Drawable d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, 20, 20, true));
        button.setBackgroundColor(Color.CYAN);
        if(resourceId!=0)
            button.setImageResource(resourceId);
        button.setScaleType(ImageView.ScaleType.FIT_XY);
        //button.setBackground(d);
        //button.setText("");
    }
    public void makeButtonIncorrectImage(Button button)
    {
        Drawable dr = context.getResources().getDrawable(R.drawable.incorrect);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        Drawable d = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, 20, 20, true));
        button.setBackground(d);
        button.setText("");
    }
    public void buildTable(int rows, int cols, ImageButton table[][], TableLayout table_layout,int resId) //table button allocation
    {

        //Every button has same listener
        int tagNumber = 0;
        for (int i = 0; i < rows; i++)
            table[i] = new ImageButton[cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                table[i][j] = new ImageButton(context);
                table[i][j].setTag(tagNumber++);
                table[i][j].setBackgroundColor(Color.TRANSPARENT);
                table[i][j].setImageResource(resId);
                if(rows == 5)
                    table[i][j].setAdjustViewBounds(true);
                table[i][j].setScaleType(ImageView.ScaleType.FIT_XY);
            }

        Log.i("layout",String.valueOf(rows));
        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(context);
            for (int j = 0; j < cols; j++) {
                table[i][j].setMinimumHeight(65);
                row.addView(table[i][j]);
            }
            table_layout.addView(row);
        }
    }

    public int[] pickRandomBlocks(int pickedSize) {
        int pickedRandomBlocks[] = new int[pickedSize];
        for (int i = 0; i < pickedSize; i++) {
            pickedRandomBlocks[i] = (int) (Math.random() * 25);
            for (int j = 0; j < i; j++) {
                while (pickedRandomBlocks[i] == pickedRandomBlocks[j]) {
                    pickedRandomBlocks[i] = (int) (Math.random() * 25);
                    j = 0;
                }
            }
        }
        for (int i = 0; i < pickedSize; i++)
            Log.i("test", "pickedRandomBlock "+String.valueOf(pickedRandomBlocks[i]));
        return pickedRandomBlocks;
    }

    public int[] pickRandomNumbers(int pickedSize) {
        int pickedRandomNumbers[] = new int[pickedSize];
        for (int i = 0; i < pickedSize; i++) {
            pickedRandomNumbers[i] = (int) (Math.random() * 10);
            for (int j = 0; j < i; j++) {
                while (pickedRandomNumbers[i] == pickedRandomNumbers[j]) {
                    pickedRandomNumbers[i] = (int) (Math.random() * 10);
                    j = 0;
                }
            }
        }
        for (int i = 0; i < pickedSize; i++)
            Log.i("test", "pickedRandomNumbers "+String.valueOf(pickedRandomNumbers[i]));
        return pickedRandomNumbers;
    }

    public void cleanTable(ImageButton table[][], int ROWS, int COLS,int resId) {
        for(int i=0;i<ROWS;i++)
            for(int j=0;j<COLS;j++) {
                table[i][j].setImageResource(resId);
                table[i][j].setBackgroundColor(Color.TRANSPARENT);
            }
    }
    public void disableClickBlocks(Boolean bool, ImageButton table[][],int ROWS,int COLS) {
        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                table[i][j].setClickable(!bool);
    }
}
