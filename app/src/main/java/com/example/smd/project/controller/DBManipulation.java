package com.example.smd.project.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.smd.project.model.Food;

import java.util.ArrayList;

import static com.example.smd.project.controller.DBHelper.IMAGEURL;
import static com.example.smd.project.controller.DBHelper.ITEMID;
import static com.example.smd.project.controller.DBHelper.ITEMNAME;
import static com.example.smd.project.controller.DBHelper.PRICE;
import static com.example.smd.project.controller.DBHelper.QUANTITY;
import static com.example.smd.project.controller.DBHelper.TABLENAME;

public class DBManipulation {
    private DBHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private Context mContext;
    private static String mDBName;
    private static DBManipulation mInstance;

    public DBManipulation(Context context) {
        mDBName = DBHelper.DATABASE_NAME;
        this.mContext = context;
        mDBHelper = new DBHelper(context);
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDb(){
        return mSQLiteDatabase;
    }

    public static DBManipulation getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBManipulation(context);
        }
        return mInstance;
    }


    public void addAll(){
        ArrayList<Integer> foodsList = ShoppingCartItem.getInstance(mContext).getFoodInCart();
        for (Integer i : foodsList){
            Food curFood = ShoppingCartItem.getInstance(mContext).getFoodById(i);
            String insertCurrentFood = "INSERT INTO " + TABLENAME + "("
                                    + ITEMID + ","
                                    + ITEMNAME + ","
                                    + QUANTITY + ","
                                    + PRICE + ","
                                    + IMAGEURL
                                    + ")"
                                    + "VALUES ("
                                    + curFood.getId() + ","
                                    + "'" + curFood.getName() + "'" + ","
                                    + ShoppingCartItem.getInstance(mContext).getFoodNumber(curFood) + ","
                                    + curFood.getPrice() + ","
                                    + "'" + ","
                                    + "'" + curFood.getImageName() + "'"
                                    + ")";
            Log.e("DB", "insert value query: " + insertCurrentFood);
            mSQLiteDatabase.execSQL(insertCurrentFood);
        }
    }


    public void deleteAll(){
        mSQLiteDatabase.execSQL("delete from "+ TABLENAME);
        Log.e("DB", "Delete all: " + "delete from "+ TABLENAME);
    }


}
