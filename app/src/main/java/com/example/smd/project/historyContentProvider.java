package com.example.smd.project;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;

import android.database.Cursor;
import android.database.SQLException;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class historyContentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.smd.project.historyContentProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/history";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String TABLENAME = "history";
    public static final String ITEMID = "id";
    public static final String ITEMNAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String TOTALORDER = "price";
    public static final String ORDERDATE = "orderdate";
    public static final String ORDERSTATUS = "orderstatus";
    public static final String IMAGENAME = "image";

    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "Db";

    private SQLiteDatabase db;


    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e("sqlite", "create table");
            String createTable = " CREATE TABLE " + TABLENAME + "("
                    + ITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEMNAME + " TEXT, "
                    + QUANTITY + " INTEGER, " + TOTALORDER + " DECIMAL(10,2), " + " TEXT, "+ ORDERDATE+ " TEXT, "+ ORDERSTATUS+ " TEXT, " + IMAGENAME + " TEXT)";
            Log.e("DB", "create table query: " + createTable);
//                + DATE + " TEXT, " + ADDRESS + " TEXT,"+ MOBILE + " INTEGER, "+ STATUS + " INTEGER)";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */

        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = db.insert(TABLENAME, "", values);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection,
                        String selection,String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLENAME);

        if (sortOrder == null || sortOrder == ""){
            /**
             * By default sort on student names
             */
            sortOrder = ITEMID;
        }

        Cursor c = qb.query(db,	projection,	selection,
                selectionArgs,null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        count = db.delete(TABLENAME, selection, selectionArgs);
        Toast.makeText(getContext(),
                TABLENAME +selection+selectionArgs + count, Toast.LENGTH_LONG).show();


        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values,
                      String selection, String[] selectionArgs) {
        int count = 0;
        count = db.update("attendance", values, selection, selectionArgs);
        /*Toast.makeText(getContext(),
                ATTENDANCE_TABLE_NAME + values + selection +selectionArgs + count, Toast.LENGTH_LONG).show();*/
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}