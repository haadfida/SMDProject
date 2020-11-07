package com.example.smd.project.model;

public class Food {
    private int mId;
    private String mName;
    private String mRecepiee;
    private double mPrice;
    private String mCity;
    private String mImageName;
    private int mImage;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRecepiee() {
        return mRecepiee;
    }

    public void setRecepiee(String recepiee) {
        mRecepiee = recepiee;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getImageName() {
        return mImageName;
    }

    public int getImage(){
        return mImage;
    }

    public void setImage(int image) {
        this.mImage = image;
    }

    public void setImageUrl(String imageUrl) {
        mImageName = imageUrl;
    }
}
