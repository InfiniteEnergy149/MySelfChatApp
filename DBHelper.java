package com.example.myselfchatapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "myselfchatapp.db", null, 1);
    }

    private static final String TABLE_NAME = "user_data";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_LOGO = "logo";
    private static final String COLUMN_COLOUR = "colour";

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_LOGO + " TEXT, " +
                COLUMN_COLOUR + " TEXT);";
        DB.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists " + TABLE_NAME + "");
    }

    public Boolean insertUserData(String name, String password, Integer age, String description, String logo, String colour) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("age", age);
        contentValues.put("description", description);
        contentValues.put("logo", logo);
        contentValues.put("colour", colour);
        long result=DB.insert(TABLE_NAME, null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean updateUserData(String name, String password, Integer age, String description, String logo, String colour) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("age", age);
        contentValues.put("description", description);
        contentValues.put("logo", logo);
        contentValues.put("colour", colour);

        Cursor cursor = DB.rawQuery("Select * from " + TABLE_NAME+ " where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update(TABLE_NAME, contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Boolean deleteData (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + TABLE_NAME+ " where name = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete(TABLE_NAME, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getData ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + TABLE_NAME + "", null);
        return cursor;
    }
}
