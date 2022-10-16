package com.example.myselfchatapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "myselfchatapp5.db", null, 1);
    }

    private static final String USER_TABLE_NAME = "user_data";
    private static final String USER_ID = "_id";
    private static final String USER_NAME = "name";
    private static final String USER_PASSWORD = "password";
    private static final String USER_AGE = "age";
    private static final String USER_DESCRIPTION = "description";
    private static final String USER_LOGO = "logo";
    private static final String USER_COLOUR = "colour";

    private static final String GROUP_TABLE_NAME = "group_data";
    private static final String GROUP_ID = "_id";
    private static final String GROUP_NAME = "name";

    private static final String GROUP_USER_X_TABLE_NAME = "group_user_x_data";
    private static final String GROUP_USER_X_ID = "_id";
    private static final String GROUP_USER_X_USER_ID = "user_id";
    private static final String GROUP_USER_X_GROUP_ID = "group_id";
    private static final String GROUP_USER_X_LAST_MESSAGE_ID = "last_message_id";

    private static final String MESSAGE_TABLE_NAME="message_data";
    private static final String MESSAGE_ID = "_id";
    private static final String MESSAGE_SENDER_USER_ID = "sender_user_id";
    private static final String MESSAGE_GROUP_ID = "group_id";
    private static final String MESSAGE_CONTENT = "content";
    private static final String MESSAGE_DATE = "date";
    private static final String MESSAGE_TIME = "time";

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String userQuery = "CREATE TABLE " + USER_TABLE_NAME + " (" +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_NAME + " TEXT, " +
                USER_PASSWORD + " TEXT, " +
                USER_AGE + " INTEGER, " +
                USER_DESCRIPTION + " TEXT, " +
                USER_LOGO + " TEXT, " +
                USER_COLOUR + " TEXT);";
        DB.execSQL(userQuery);

        String groupQuery = "CREATE TABLE " + GROUP_TABLE_NAME + " (" +
                GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GROUP_NAME + " TEXT);";
        DB.execSQL(groupQuery);

        String groupUserXQuery = "CREATE TABLE " + GROUP_USER_X_TABLE_NAME + " (" +
                GROUP_USER_X_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GROUP_USER_X_USER_ID + " INTEGER, " +
                GROUP_USER_X_GROUP_ID + " INTEGER, " +
                GROUP_USER_X_LAST_MESSAGE_ID + " INTEGER);";
        DB.execSQL(groupUserXQuery);

        String messageQuery = "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
                MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MESSAGE_SENDER_USER_ID + " INTEGER, " +
                MESSAGE_GROUP_ID + " INTEGER, " +
                MESSAGE_CONTENT + " TEXT, " +
                MESSAGE_DATE + " TEXT, " +
                MESSAGE_TIME + " TEXT);";
        DB.execSQL(messageQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
   //     DB.execSQL("drop Table if exists " + USER_TABLE_NAME + "");
        Log.d("myselfchatapp","NOT USED");
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
        if (name == null||password == null||age == null||description==null||logo==null||colour==null) {
                return false;
        }else{
            long result = DB.insert(USER_TABLE_NAME, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

        public Boolean insertGroupData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        if (name == null) {
            return false;
        }else{
            long result = DB.insert(GROUP_TABLE_NAME, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Boolean insertGroupUserXData(Integer userId, Integer groupId, String lastMessageId) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", userId);
        contentValues.put("group_id", groupId);
        contentValues.put("last_message_id", lastMessageId);
        if (userId == null||groupId == null|| lastMessageId == null) {
            return false;
        }else{
            long result = DB.insert(GROUP_USER_X_TABLE_NAME, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Boolean insertMessageData(Integer senderUserId, Integer groupId, String content, String date, String time) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sender_user_id", senderUserId);
        contentValues.put("group_id", groupId);
        contentValues.put("content", content);
        contentValues.put("date", date);
        contentValues.put("time", time);
        if (senderUserId == null||groupId == null||content == null||date == null||time == null) {
            return false;
        }else{
            long result = DB.insert(MESSAGE_TABLE_NAME, null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
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
        if (name == null||password == null||age == null||description==null||logo==null||colour==null) {
            return false;
        }else {
            Cursor cursor = DB.rawQuery("Select * from " + USER_TABLE_NAME + " where name = ?", new String[]{name});
            if (cursor.getCount() > 0) {
                long result = DB.update(USER_TABLE_NAME, contentValues, "name=?", new String[]{name});
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }
/*
    public Boolean updateGroupData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        if (name == null) {
            return false;
        }else {
            Cursor cursor = DB.rawQuery("Select * from " + GROUP_TABLE_NAME + " where name = ?", new String[]{name});
            if (cursor.getCount() > 0) {
                long result = DB.update(GROUP_TABLE_NAME, contentValues, "name=?", new String[]{name});
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    public Boolean updateGroupData(Integer groupUserXId, Integer userId, Integer groupId, Integer lastMessageId) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("age", age);
        contentValues.put("description", description);
        if (name == null||password == null||age == null||description==null) {
            return false;
        }else {
            Cursor cursor = DB.rawQuery("Select * from " + USER_TABLE_NAME + " where name = ?", new String[]{name});
            if (cursor.getCount() > 0) {
                long result = DB.update(USER_TABLE_NAME, contentValues, "name=?", new String[]{name});
                if (result == -1) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        }
    }*/

    public Boolean deleteUserData (String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + USER_TABLE_NAME+ " where _id = ?", new String[]{id});
        if (cursor.getCount() > 0) {
            long result = DB.delete(USER_TABLE_NAME, "_id=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteGroupData (String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + GROUP_TABLE_NAME+ " where _id = ?", new String[]{id});
        if (cursor.getCount() > 0) {
            long result = DB.delete(GROUP_TABLE_NAME, "_id=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public Boolean deleteGroupUserXData (String userId) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + GROUP_USER_X_TABLE_NAME+ " where user_id = ?", new String[]{userId});
        if (cursor.getCount() > 0) {
            long result = DB.delete(GROUP_USER_X_TABLE_NAME, "user_id=?", new String[]{userId});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getUserData () {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + USER_TABLE_NAME + "", null);
        return cursor;
    }

    public Cursor getMessageData () {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + MESSAGE_TABLE_NAME + "", null);
        return cursor;
    }

    public Cursor getGroupData () {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + GROUP_TABLE_NAME + "", null);
        return cursor;
    }

    public Cursor getGroupUserXData () {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + GROUP_USER_X_TABLE_NAME + "", null);
        return cursor;
    }
}
