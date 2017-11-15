package brainandcognitionlab.bac.Model;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import brainandcognitionlab.bac.Controller.DB.DBHelper;
import brainandcognitionlab.bac.Controller.DB.Record;
import brainandcognitionlab.bac.Controller.RoundController.RoundStatus;

/**
 * Created by hojaeson on 4/4/16.
 */
public class AlertFunctions {
    private Context context;
    private DBHelper dbHelper = DBHelper.getSingleton_DB_Instance(context);
    public AlertFunctions(Context context)
    {
        this.context = context;
    }

    public void alertFailDialogWithTransition(final Intent nextIntent) {
        RoundStatus rs = nextIntent.getExtras().getParcelable("rs");
        //Game game = dbHelper.selectDataFromGameTableIdx(6);//nextIntent.getExtras().getParcelable("gameId"));
        new Thread(new Runnable(){
            public void run(){
                Record record = nextIntent.getExtras().getParcelable("record");
                dbHelper.insertDataToRecordTable(record);
            }
        }).start();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.context).setTitle("실패").
                setMessage("다음 단계로 이동하시겠습니까?").
                setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).
                setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(nextIntent);
                        ((Activity)context).finish();
                    }
                });
            alertDialog.show();
    }

    public void alertSuccessDialogWithTransition(final Intent nextIntent) {
        final RoundStatus rs = nextIntent.getExtras().getParcelable("rs");
        new Thread(new Runnable(){
            public void run(){
                Record record = nextIntent.getExtras().getParcelable("record");
                if(rs.getNumOfStars() == 0){
                    record.setLEVEL(String.valueOf(Integer.parseInt(record.getLEVEL())-1));
                }
                dbHelper.insertDataToRecordTable(record);
            }
        }).start();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.context).setTitle("성공").
                setMessage("다음 단계로 이동하시겠습니까?").
                setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false).
                setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(nextIntent);
                        ((Activity)context).finish();
                    }
                });

        alertDialog.show();

    }
}
