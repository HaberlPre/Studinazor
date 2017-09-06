package com.gastell_gehr_haberl.studinazor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lucas on 06.09.2017.
 */

public class ToDoListeDBDB {

    private static final String DATABASE_NAME = "toDoListDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "todoListDBs";

    public static final String KEY_ID = "_id";
    public static final String KEY_LIST = "liste";

    public static final int COLUMN_TASK_INDEX = 1;

    private ToDoListeDBDB.ToDoDBDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public ToDoListeDBDB(Context context) {
        dbHelper = new ToDoListeDBDB.ToDoDBDBOpenHelper(context, DATABASE_NAME, null,
                DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

    public long insertLists(ToDoListeDatenbank list) {
        ContentValues newItems = new ContentValues();
        newItems.put(KEY_LIST, list.getName());
        return db.insert(DATABASE_TABLE, null, newItems);
    }

    public ArrayList<ToDoListeDatenbank> getAllToDoListDB() {
        ArrayList<ToDoListeDatenbank> lists = new ArrayList<ToDoListeDatenbank>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {
                KEY_ID, KEY_LIST}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                //Context task = cursor.getString(COLUMN_TASK_INDEX); //TODO string <-> context

                //lists.add(new ToDoListeDatenbank(task));

            } while (cursor.moveToNext());
        }
        return lists;
    }

    public void removeToDoListDB(ToDoListeDatenbank list) {

        String toDelete = KEY_LIST + "=?";
        String[] deleteArguments = new String[]{list.getName()};
        db.delete(DATABASE_TABLE, toDelete, deleteArguments);

    }

    private class ToDoDBDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_LIST
                + " text not null);";

        public ToDoDBDBOpenHelper(Context c, String dbname,
                                SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
