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


    //Konstanten
    private static final String DATABASE_NAME = "einkaufsliste.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "shoppingitems";

    public static final String KEY_ID = "_id";
    public static final String KEY_ITEM = "item";
    public static final String KEY_AMOUNT = "amount";
    public static final String KEY_UNIT = "unit";

    public static final int COLUMN_AMOUNT_INDEX = 1;
    public static final int COLUMN_UNIT_INDEX = 2;
    public static final int COLUMN_ITEM_INDEX = 3;


    //Variablen
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

    /**
     * Fügt einen neuen Artikel hinzu
     * @param item
     * @return
     */
    public long insertItem(ShopItem item) {
        ContentValues itemValues = new ContentValues();
        itemValues.put(KEY_AMOUNT, item.getAmount());
        itemValues.put(KEY_UNIT, item.getUnit());
        itemValues.put(KEY_ITEM, item.getName());
        return db.insert(DATABASE_TABLE, null, itemValues);
    }

    /**
     * Löscht ein Artikel aus der Liste
     * @param item
     */
    public void removeShopItem(ShopItem item) {
        String toDelete = KEY_ITEM + "=?";
        String[] deleteArguments = new String[]{item.getName()};
        db.delete(DATABASE_TABLE, toDelete, deleteArguments);
    }

    /**
     * Löscht alle Artikel aus der Liste
     */
    public void removeAllItems(){
        db =  dbHelper.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
    }

    /**
     * Überarbeitet den Artikel und speichert ihn neu
     * @param amount
     * @param unit
     * @param name
     * @param item
     */
    public void updateShopItem(String amount, String unit, String name, ShopItem item){
        db = dbHelper.getWritableDatabase();
        //the new values
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_AMOUNT, amount);
        newValues.put(KEY_UNIT,unit);
        newValues.put(KEY_ITEM,name);
        //which row should be updated (the ? will be the item.getName()-Argument)
        String toUpdate = KEY_ITEM + "=?";
        String[] updateArgument = new String[]{item.getName()};

        db.update(DATABASE_TABLE, newValues, toUpdate, updateArgument);
    }

    /**
     * Gibt alle Artikel in einer Liste zurück, die gespeichert wurden
     * @return Liste mit den Artikel
     */
    public ArrayList<ShopItem> getAllShopItems() {
        ArrayList<ShopItem> items = new ArrayList<ShopItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_AMOUNT, KEY_UNIT,
                KEY_ITEM}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String amount = cursor.getString(COLUMN_AMOUNT_INDEX);
                String unit = cursor.getString(COLUMN_UNIT_INDEX);
                String item = cursor.getString(COLUMN_ITEM_INDEX);

                items.add(new ShopItem(amount, unit, item));

            } while (cursor.moveToNext());
        }
        return items;
    }

    /**
     * Hilfsklasse für die Datenbank
     */
    private class ToDoDBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_AMOUNT + " text, " + KEY_UNIT + " text, " + KEY_ITEM
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
