package com.example.smd.project.controller;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import com.example.smd.project.model.Food;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.example.smd.project.controller.DBHelper.TABLENAME;


public class ShoppingCartItem {
    private static ArrayList<Integer> foodsId;
    private static HashMap<Food, Integer> foodMap;
    private static ShoppingCartItem instance = null;
    private int totalNumber;
    private int totalPrice;

    ShoppingCartItem(){

        foodsId = new ArrayList<Integer>();
        foodMap = new HashMap<Food, Integer>();
        totalNumber = 0;
        totalPrice = 0;
    }

    ShoppingCartItem(ArrayList<Integer> foodsId, HashMap<Food, Integer> foodMap, int totalNumber, int totalPrice){
        this.foodsId = foodsId;
        this.foodMap = foodMap;
        this.totalNumber = totalNumber;
        this.totalPrice = totalPrice;
    }

    public void clear(){
        foodMap.clear();
        totalNumber = 0;
        totalPrice = 0;
        foodsId.clear();
    }

    public ArrayList<Integer> getFoodInCart(){
        return foodsId;
    }

    public void setNull(){
        instance = null;
    }

    public static synchronized ShoppingCartItem getInstance(Context context){
        if (instance == null){
            Cursor cursor = DBManipulation.getInstance(context).getDb()
                    .rawQuery("SELECT * FROM " + TABLENAME, null);
            if (cursor.getCount() > 0){
                final ArrayList<Integer> idList = new ArrayList<Integer>();
                final HashMap<Food, Integer> foodInDb = new HashMap<Food, Integer>();
                int totalNumberInDb = 0;
                int totalPriceInDb = 0;
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String curName = cursor.getString(cursor.getColumnIndex(DBHelper.ITEMNAME));
                    final int curId = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.ITEMID)));
                    final int curQuantity = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.QUANTITY)));
                    double curPrice = Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.PRICE)));
                    String curUrl = cursor.getString(cursor.getColumnIndex(DBHelper.IMAGEURL));
                    final Food curFood = new Food();
                    curFood.setId(curId);
                    curFood.setName(curName);
                    curFood.setPrice(curPrice);
                    curFood.setImageUrl(curUrl);

                    totalNumberInDb += curQuantity;
                    totalPriceInDb += (curFood.getPrice() * curQuantity);
                    cursor.moveToNext();
                }
                DBManipulation.getInstance(context).deleteAll();
                instance = new ShoppingCartItem(idList, foodInDb, totalNumberInDb, totalPriceInDb);
            }
            else {
                instance = new ShoppingCartItem();
            }
        }
        return instance;
    }

    public void addToCart(Food food){
        if (foodMap.containsKey(food)){
            int curNum = foodMap.get(food) + 1;
            foodMap.put(food, curNum);
        }
        else {
            foodsId.add(food.getId());
            foodMap.put(food, 1);
        }
        totalPrice += food.getPrice();
        totalNumber++;
    }

    public Food getFoodById(int id){
        if (foodsId.contains(id)){
            Iterator it = foodMap.keySet().iterator();
            while (it.hasNext()){
                Food curFood = (Food) it.next();
                int foodNumber = foodMap.get(curFood);
                if (curFood.getId() == id){
                    return curFood;
                }
            }
        }
        Log.e("GET FOOD BY ID", "No such item found");
        return null;
    }

    public void setFoodNumber(Food food, int number){
        int curNumber = foodMap.get(food);
        int numberChange = Math.abs(curNumber - number);
        if (number > curNumber){
            totalNumber += numberChange;
            totalPrice += numberChange * food.getPrice();
        }
        else{
            totalNumber -= numberChange;
            totalPrice -= numberChange * food.getPrice();
        }
        if (number == 0){
            foodMap.remove(food);
            for (int i=0; i<foodsId.size(); i++){
                if (foodsId.get(i) == food.getId()){
                    foodsId.remove((Object)foodsId.get(i));
                    break;
                }
            }
            return;
        }
        foodMap.put(food, number);
    }

    public double getPrice(){
        return totalPrice;
    }

    public int getFoodNumber(Food food){
        if (foodMap.containsKey(food)){
            return foodMap.get(food);
        }
        return 0;
    }


    public int getSize(){
        return totalNumber;
    }

    public int getFoodTypeSize(){
        return foodsId.size();
    }


    public void placeOrder(String addr, String mobile){
        Iterator it = foodMap.keySet().iterator();
        while (it.hasNext()){
            Food curFood = (Food) it.next();
            int cnt = foodMap.get(curFood);
            Log.e(curFood.getName(), "" + cnt);
            //objRequestMethod(curFood, cnt, addr, mobile);
        }
    }

    public void addToDb(Context context){
        DBManipulation.getInstance(context).addAll();
    }

}