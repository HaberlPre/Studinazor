package com.gastell_gehr_haberl.studinazor;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Tanja on 22.08.2017.
 */

public class EinkaufslisteDatabase {

    private static final String DATABASE_NAME = "einkaufsliste.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "shoppingitems";

    public static final String KEY_ID = "_id";
    public static final String KEY_ITEM = "task";

    public static final int COLUMN_TASK_INDEX = 1;

    private ToDoDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public EinkaufslisteDatabase(Context context) {
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

    public long insertItem(ShopItem item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_ITEM, item.getName());
        return db.insert(DATABASE_TABLE, null, itemValues);
    }

    public void removeToDoItem(ShopItem item) {

        String toDelete = KEY_ITEM + "=?";
        String[] deleteArguments = new String[]{item.getName()};
        db.delete(DATABASE_TABLE, toDelete, deleteArguments);

    }

    public ArrayList<ShopItem> getAllToDoItems() {
        ArrayList<ShopItem> items = new ArrayList<ShopItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
                KEY_ITEM}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String task = cursor.getString(COLUMN_TASK_INDEX);
                /*String date = cursor.getString(COLUMN_DATE_INDEX);

                Date formattedDate = null;
                try {
                    formattedDate = new SimpleDateFormat("dd.MM.yyyy",
                            Locale.GERMAN).parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance(Locale.GERMAN);
                cal.setTime(formattedDate);*/

                items.add(new ShopItem(task));

            } while (cursor.moveToNext());
        }
        return items;
    }

    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_ITEM
                + " text not null);";

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
