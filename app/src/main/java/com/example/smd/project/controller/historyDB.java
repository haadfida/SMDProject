package com.example.smd.project.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class historyDB extends SQLiteOpenHelper {
    public static final String TABLENAME = "history";
    public static final String ITEMID = "id";
    public static final String ITEMNAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String TOTALORDER = "price";
    public static final String ORDERDATE = "orderdate";
    public static final String ORDERSTATUS = "orderstatus";
    public static final String IMAGENAME = "image";

    public static final int VERSION =1;
    public static final String DATABASE_NAME = "Db";


    private Context ctx = null;

    public historyDB(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("sqlite", "create table");
        String createTable = " CREATE TABLE " + TABLENAME + "("
                + ITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEMNAME + " TEXT, "
                + QUANTITY + " INTEGER, " + TOTALORDER + " DECIMAL(10,2), " + " TEXT, "+ ORDERDATE+ " TEXT, "+ ORDERSTATUS+ " TEXT, " + IMAGENAME + " TEXT)";
        Log.e("DB", "create table query: " + createTable);
//                + DATE + " TEXT, " + ADDRESS + " TEXT,"+ MOBILE + " INTEGER, "+ STATUS + " INTEGER)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.v(DBHelper.class.getName(),
                "upgrading database from version "+ i + " to "
                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }
}
