package com.gastell_gehr_haberl.studinazor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by lucas on 05.09.2017.
 */

public class StundenplanDatabase {
    private static final String DATABASE_NAME = "stundenplan.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "stundenplanitems";

    public static final String KEY_ID = "id";
    public static final String KEY_COURSE = "course";
    public static final String KEY_DAY = "day";
    public static final String KEY_START = "startTime";
    public static final String KEY_END = "endTime";

    public static final int COLUMN_COURSE_INDEX = 1;
    public static final int COLUMN_DAY_INDEX = 2;
    public static final int COLUMN_START_INDEX = 3;
    public static final int COLUMN_END_INDEX = 4;

    private StundenplanDBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public StundenplanDatabase (Context context) {
        dbHelper = new StundenplanDBOpenHelper(context, DATABASE_NAME, null,
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

    public long insertStundenplanItem (StundenplanItem item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_COURSE, item.getCourse());
        itemValues.put(KEY_DAY, item.getDay());
        itemValues.put(KEY_START, item.getStart());
        itemValues.put(KEY_END, item.getEnd());
        return db.insert(DATABASE_TABLE, null, itemValues);
    }

    public void removeStundenplanItem (StundenplanItem item) {
        String toDelete = KEY_COURSE + "=?";
        String[] deleteArguments = new String[] {item.getCourse()};
        db.delete(DATABASE_TABLE, toDelete, deleteArguments);
    }

    public ArrayList<StundenplanItem> getAllStundenplanItems() {
        ArrayList<StundenplanItem> items = new ArrayList<StundenplanItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] {
           KEY_ID, KEY_COURSE, KEY_DAY, KEY_START, KEY_END}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String course = cursor.getString(COLUMN_COURSE_INDEX);
                String day = cursor.getString(COLUMN_DAY_INDEX);
                String start = cursor.getString(COLUMN_START_INDEX);
                String end = cursor.getString(COLUMN_END_INDEX);

                items.add(new StundenplanItem(course, day, start, end));
            } while (cursor.moveToNext());
        }
        return items;
    }

    private class StundenplanDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "+ DATABASE_TABLE
                + " ("  + KEY_ID + " integer primary key autoincrement, " + KEY_COURSE
                + " text, " + KEY_DAY + " text, " + KEY_START + " text, " + KEY_END
                + " text not null);";

        public StundenplanDBOpenHelper (Context c, String dbname,
                                        SQLiteDatabase.CursorFactory factory, int version) {
            super (c, dbname, factory, version);
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
