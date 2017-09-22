package com.gastell_gehr_haberl.studinazor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Juliane on 05.09.2017.
 */

public class ToDoListeDatenbank implements Comparable<ToDoListeDatenbank> {

    private static final String DATABASE_NAME = "toDoList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "todoListItems";

    public static final String KEY_ID = "_id";
    public static final String KEY_TASK = "task";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_BOX = "false";

    public static final int COLUMN_TASK_INDEX = 1;
    public static final int COLUMN_DATE_INDEX = 2;
    public static final int COLUMN_TIME_INDEX = 3;
    public static final int COLUMN_BOX_INDEX = 4;

    private ToDoDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public ToDoListeDatenbank(Context context) {
        dbHelper = new ToDoDBOpenHelper(context, DATABASE_NAME, null,
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

    public long insertItem(ToDoItem item) {
        ContentValues newItems = new ContentValues();
        newItems.put(KEY_TASK, item.getName());
        newItems.put(KEY_DATE, item.getFormattedDate());
        newItems.put(KEY_TIME, item.getFormattedTime());
        return db.insert(DATABASE_TABLE, null, newItems);
    }

    public ArrayList<ToDoItem> getAllToDoItems() {
        ArrayList<ToDoItem> items = new ArrayList<ToDoItem>();
        //Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_TASK, KEY_DATE}, null, null, null, null, null);
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {KEY_ID, KEY_TASK, KEY_DATE, KEY_TIME}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String task = cursor.getString(COLUMN_TASK_INDEX);
                String date = cursor.getString(COLUMN_DATE_INDEX);
                String time = cursor.getString(COLUMN_TIME_INDEX);
                Date formattedDate = null;
                Date formattedTime = null;
                try {
                    //formattedDate = new SimpleDateFormat("dd.MM.yyyy",
                    //        Locale.GERMAN).parse(date);
                    formattedDate = DateFormat.getDateInstance(DateFormat.SHORT,
                            Locale.GERMANY).parse(date);
                    formattedTime = DateFormat.getTimeInstance(DateFormat.LONG,
                            Locale.GERMANY).parse(time);
                } catch(ParseException e) {
                    e.printStackTrace();
                }
                Calendar chosenDate = Calendar.getInstance(Locale.GERMAN);
                chosenDate.setTime(formattedDate);
                Calendar chosenTime = Calendar.getInstance(Locale.GERMAN);
                chosenTime.setTime(formattedTime);

                items.add(new ToDoItem(task, chosenDate.get(Calendar.DAY_OF_MONTH),
                        chosenDate.get(Calendar.MONTH), chosenDate.get(Calendar.YEAR),
                        chosenTime.get(Calendar.SECOND), chosenTime.get(Calendar.MINUTE),chosenTime.get(Calendar.HOUR)));
                //items.add(new ToDoItem(task, chosenDate.get(Calendar.DAY_OF_MONTH),
                //        chosenDate.get(Calendar.MONTH), chosenDate.get(Calendar.YEAR)));

            } while (cursor.moveToNext());
        }
        return items;
    }

    public void removeToDoItem(ToDoItem item) {

        String toDelete = KEY_TASK + "=?";
        String[] deleteArguments = new String[]{item.getName()};
        db.delete(DATABASE_TABLE, toDelete, deleteArguments);

    }


    //Neue Version, alles löschen und bearbeiten
    public void removeAllItems(){
        db =  dbHelper.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
    }

    public void updateToDoItem(String name, ToDoItem item){
        db = dbHelper.getWritableDatabase();
        //the new values
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_TASK,name);
        //which row should be updated (the ? will be the item.getName()-Argument)
        String toUpdate = KEY_TASK + "=?";
        String[] updateArgument = new String[]{item.getName()};

        db.update(DATABASE_TABLE, newValues, toUpdate, updateArgument);
    }

    @Override
    public int compareTo(@NonNull ToDoListeDatenbank o) {
        return 0;
    }

    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TASK
                + " text, " + KEY_DATE + " text, " + KEY_TIME +  " text not null);";

        /*private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_TASK
                + " text not null, " + KEY_DATE + " text);";*/

        public ToDoDBOpenHelper(Context c, String dbname,
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
