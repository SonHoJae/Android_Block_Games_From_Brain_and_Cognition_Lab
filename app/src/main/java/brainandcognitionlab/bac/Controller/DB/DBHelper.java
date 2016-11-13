package brainandcognitionlab.bac.Controller.DB;

/**
 * Created by hojaeson on 1/5/16.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import au.com.bytecode.opencsv.CSVWriter;

public class DBHelper extends SQLiteOpenHelper {

    //Singleton variable
    private static DBHelper dbHelperSingleton = null;
    // Table Name
    public static final String USER_INFO_TABLE = "USER";
    public static final String USER_RECORD_TABLE = "RECORD";
    public static final String GAME_TABLE = "GAME";

    public static final String TABLE_ID = "TABLE_ID";
    // User Table columns
    public static final String USER_ID = "USER_ID";
    public static final String USER_ACCOUNT = "USER_ACCOUNT";
    public static final String PW = "PASSWORD";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    // GAME_RECORD Table columns
    public static final String RECORD_ID = "RECORD_ID";
    public static final String DURATION = "DURATION";
    public static final String LEVEL = "LEVEL";
    public static final String SUCCESS = "SUCCESS";
    public static final String START_DATE = "START_DATE";
    public static final String END_DATE = "END_DATE";
    /* GAME_ID WOULD BE THE FOREIGN KEY */
    // GAME Table columns
    public static final String GAME_ID = "GAME_ID";
    public static final String GAME_NAME = "GAME_NAME";
    public static final String GAME_TYPE = "GAME_TYPE";
    // Database Information
    static final String DB_NAME = "BAC_DATABASE";

    // database version
    static final int DB_VERSION = 2;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //DB is Singleton variable
    public static DBHelper getSingleton_DB_Instance(Context ctx) {
        if (dbHelperSingleton == null) {
            dbHelperSingleton = new DBHelper(ctx.getApplicationContext());
        }
        return dbHelperSingleton;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_INFO_TABLE = "create table if not exists " + USER_INFO_TABLE + "(" + USER_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_ACCOUNT + " TEXT NOT NULL, "+ PW + " TEXT NOT NULL, " + NAME + " TEXT NOT NULL, " + EMAIL + " TEXT NOT NULL )";
        db.execSQL(CREATE_USER_INFO_TABLE);

        String CREATE_USER_RECORD_TABLE = "create table if not exists " + USER_RECORD_TABLE + "(" + RECORD_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT , " + DURATION + " TEXT NOT NULL, "
                + LEVEL + " TEXT NOT NULL, "+SUCCESS+" TEXT NOT NULL ," +USER_ACCOUNT+" TEXT NOT NULL, "+GAME_ID+" INTEGER NOT NULL, "
                + START_DATE +" DATE ,"+END_DATE+" DATE)";

        String CREATE_GAME_TABLE = "create table if not exists " + GAME_TABLE + "(" + GAME_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GAME_NAME + " TEXT NOT NULL, " + GAME_TYPE + " TEXT NOT NULL);";
        //유저정보, 게임기록정보, 게임타입정보 테이블 생성
        db.execSQL(CREATE_USER_INFO_TABLE);
        db.execSQL(CREATE_USER_RECORD_TABLE);
        db.execSQL(CREATE_GAME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_INFO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_RECORD_TABLE);
        onCreate(db);
    }
    //UserTable operation
    public void insertDataToUserTable(User user_info) {
        String INSERT_USER_INFO_TABLE = "INSERT INTO " + USER_INFO_TABLE + " ( " + USER_ACCOUNT +","+ PW + "," + NAME + "," + EMAIL
                + ") VALUES ('"
                +user_info.getAccount() +"','"+ user_info.getPASSWORD() + "','" + user_info.getNAME() + "','" + user_info.getEMAIL() + "')";
        Log.i("database","insertDataToUserTable_query : "+ INSERT_USER_INFO_TABLE);
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(INSERT_USER_INFO_TABLE);
        stmt.execute();
        stmt.close();
        db.close();
    }

    public void selectDataFromUserTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + USER_ID + "," + PW + "," + NAME + "," + EMAIL + " FROM " + USER_INFO_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            for (int i = 0; i < 4; i++)
                Log.i("user_info", cursor.getString(i) + " " + "test");
            Log.i("user_info", "\n");

        }
        cursor.close();
        db.close();
    }

    public void deleteDataFromUserTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteFromUserTable = "delete from " + USER_INFO_TABLE;
        SQLiteStatement stmt = db.compileStatement(deleteFromUserTable);
        stmt.execute();
        stmt.close();
        db.close();
    }

    public void dropUserTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String dropUserTable = "DROP TABLE IF EXISTS " + USER_INFO_TABLE;
        db.execSQL(dropUserTable);
    }

    public String getUserName(String userAccount) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + NAME + " FROM " + USER_INFO_TABLE
                + " WHERE "+USER_ACCOUNT+" = '"+userAccount+"'";
        Log.i("database","getUserName_query : "+query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        String userName = cursor.getString(0);
        cursor.close();
        db.close();
        return userName;
    }

    public String getUserEmail(String userAccount) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + EMAIL + " FROM " + USER_INFO_TABLE
                + " WHERE "+USER_ACCOUNT+" = '"+userAccount+"'";
        Log.i("database","getUserName_query : "+query);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        String userEmail = cursor.getString(0);
        cursor.close();
        db.close();
        return userEmail;
    }
    //GameTable operation
    public void insertDataToGameTable(Game game_info) {
        String INSERT_GAME_INFO_TABLE = "INSERT INTO " + GAME_TABLE + " ( " + GAME_NAME + "," + GAME_TYPE + ") VALUES ('"
                +game_info.getGAME_NAME() + "','"  + game_info.getGAME_TYPE() + "')";
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(INSERT_GAME_INFO_TABLE);
        stmt.execute();
        stmt.close();
        db.close();
    }

    public boolean selectDataFromGameTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + GAME_ID + "," + GAME_NAME + "," + GAME_TYPE + " FROM " + GAME_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        boolean isEmpty = true;
        while (cursor.moveToNext()) {
            for (int i = 0; i < 3; i++)
                Log.i("game", cursor.getString(i) + " ");
            Log.i("game", "\n");
            isEmpty = false;
        }
        cursor.close();
        db.close();
        return isEmpty;
    }
    public int getGameId(Game game) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + GAME_ID + " FROM " + GAME_TABLE
                + " WHERE "+GAME_NAME+" = '"+game.getGAME_NAME()+"'";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            game.setGAME_ID(Integer.parseInt(cursor.getString(0)));
        }
        cursor.close();
        db.close();
        return game.getGAME_ID();
    }

    public String getGameName(int gameid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + GAME_NAME + " FROM " + GAME_TABLE
                + " WHERE "+GAME_ID+" = "+String.valueOf(gameid);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        String gameName = cursor.getString(0);
        cursor.close();
        db.close();
        return gameName;
    }

    public String getGameType(int gameid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + GAME_TYPE + " FROM " + GAME_TABLE
                + " WHERE "+GAME_ID+" = "+String.valueOf(gameid);
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        String gameType = cursor.getString(0);
        cursor.close();
        db.close();
        return gameType;
    }
    //RecordTable operation
    public void insertDataToRecordTable(Record record_info) {
        Log.i("duration","duration = "+record_info.getDURATION());
        String INSERT_RECORD_INFO_TABLE = "INSERT INTO " + USER_RECORD_TABLE + " ( " +  DURATION + "," + LEVEL + ","+ SUCCESS + ","+ USER_ACCOUNT +","+GAME_ID+","
                                          + START_DATE + ","+ END_DATE + " ) VALUES ( '"
                + record_info.getDURATION() + "','" + record_info.getLEVEL() +"','"+record_info.isSuccess()+"','"+record_info.getUserAccount()+"','"
                +record_info.getGameId()+ "','" +record_info.getStartDate()+"','"+record_info.getEndDate()+"')";
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(INSERT_RECORD_INFO_TABLE);
        stmt.execute();
        stmt.close();
        db.close();
    }

    public void selectDataFromRecordTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + RECORD_ID + "," + DURATION + "," + LEVEL + "," + GAME_ID+ " FROM " + USER_RECORD_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            for (int i = 0; i < 4; i++)
                Log.i("record","record"+ cursor.getString(i) + " ");
            Log.i("record", "\n");

        }
        cursor.close();
        db.close();
    }
    public void dropRecordTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String dropUserTable = "DROP TABLE IF EXISTS " + USER_RECORD_TABLE;
        db.execSQL(dropUserTable);
    }

    //Export data to CSV file
    public void exportDB() throws FileNotFoundException, UnsupportedEncodingException {

        //File dbFile=getDatabasePath("MyDBName.db");
        //DBHelper dbhelper = new DBHelper(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
 
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "abc.csv");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            CSVWriter csvWrite = new CSVWriter(out);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor userCSV = db.rawQuery("SELECT * FROM " + USER_INFO_TABLE, null);
            Cursor recordCSV = db.rawQuery("SELECT * FROM " + USER_RECORD_TABLE, null);
            Cursor gameCSV = db.rawQuery("SELECT * FROM " + GAME_TABLE, null);
            /*Recordid,name,email,duration,lavel,success,gamename,gametype*/
            String columnName[] ={"ID","NAME","EMAIL","DURATION","LEVEL","SUCCESS","GAME_NAME","GAME_TYPE","START_DATE","END_DATE"};
            csvWrite.writeNext(columnName);
            gameCSV.moveToNext();
            userCSV.moveToNext();
            //recordCSV.getString(4) -> USER ID
            //recordCSV.getString(5) -> GAME ID
            while (recordCSV.moveToNext()) {
                String arrStr[] = {recordCSV.getString(4), this.getUserName(recordCSV.getString(4)), this.getUserEmail(recordCSV.getString(4)),
                        recordCSV.getString(1), recordCSV.getString(2), recordCSV.getString(3),
                        this.getGameName(Integer.parseInt(recordCSV.getString(5))), this.getGameType(Integer.parseInt(recordCSV.getString(5))),recordCSV.getString(6),recordCSV.getString(7)};
                Log.i("database","Export to csv_info : "+arrStr);
                csvWrite.writeNext(arrStr);
            }

            recordCSV.close();
            gameCSV.close();
            userCSV.close();
            csvWrite.close();
        } catch (Exception sqlEx) {
            Log.e("record", sqlEx.getMessage(), sqlEx);
        }
    }
}