package com.example.smd.project.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.smd.project.model.Food;
import com.example.smd.project.model.Order;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.smd.project.controller.historyDB.IMAGENAME;
import static com.example.smd.project.controller.historyDB.ITEMID;
import static com.example.smd.project.controller.historyDB.ITEMNAME;
import static com.example.smd.project.controller.historyDB.TOTALORDER;
import static com.example.smd.project.controller.historyDB.ORDERDATE;
import static com.example.smd.project.controller.historyDB.ORDERSTATUS;
import static com.example.smd.project.controller.historyDB.QUANTITY;
import static com.example.smd.project.controller.historyDB.TABLENAME;

public class historyDBManipulation {
    private historyDB mhistoryDB;
    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;
    private static String mDBName;
    private static historyDBManipulation mInstance;

    public historyDBManipulation(Context context) {
        mDBName = DBHelper.DATABASE_NAME;
        this.mContext = context;
        mhistoryDB = new historyDB(context);
        mSQLiteDatabase = mhistoryDB.getWritableDatabase();
    }

    public SQLiteDatabase getDb(){
        return mSQLiteDatabase;
    }

    public static historyDBManipulation getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new historyDBManipulation(context);
        }
        return mInstance;
    }


    public void addOrder(){
        ArrayList<Integer> foodsList = ShoppingCartItem.getInstance(mContext).getFoodInCart();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        for (Integer i : foodsList){
            Food curFood = ShoppingCartItem.getInstance(mContext).getFoodById(i);
            String insertorder = "INSERT INTO " + TABLENAME + "("
                    + ITEMNAME + ","
                    + QUANTITY + ","
                    + TOTALORDER + ","
                    + ORDERDATE + ","
                    + ORDERSTATUS + ","
                    + IMAGENAME
                    + ")"   + "VALUES ("
                    +  "'" + curFood.getName() + "'" + ","
                    + ShoppingCartItem.getInstance(mContext).getFoodNumber(curFood) + ","
                    + curFood.getPrice()*ShoppingCartItem.getInstance(mContext).getFoodNumber(curFood) + ","
                    + "'" + formattedDate + "'" + ","
                    + "'" + "approved" + "'" + ","
                    + "'" + curFood.getImageName() + "'"
                    + ")";
            mSQLiteDatabase.execSQL(insertorder);


        }
    }


    public ArrayList<Order> retrieveOrder(){
        ArrayList<Order> ordersList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLENAME;
        Cursor cursor      = mSQLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Order curOrder = new Order();
                curOrder.setId(cursor.getInt(cursor.getColumnIndex(ITEMID)));
                curOrder.setName(cursor.getString(cursor.getColumnIndex(ITEMNAME)));
                //curOrder.setImageUrl(cursor.getString(cursor.getColumnIndex(IMAGENAME)));
                curOrder.setTotal(cursor.getDouble(cursor.getColumnIndex(TOTALORDER)));
                curOrder.setQuantity(cursor.getInt(cursor.getColumnIndex(QUANTITY)));
                curOrder.setDate(cursor.getString(cursor.getColumnIndex(ORDERDATE)));
                curOrder.setStatus(cursor.getString(cursor.getColumnIndex(ORDERSTATUS)));

                ordersList.add(curOrder);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return ordersList;
    }

    public void deleteAll(){
        mSQLiteDatabase.execSQL("delete from "+ TABLENAME);
        Log.e("DB", "Delete all: " + "delete from "+ TABLENAME);
    }


}
