package com.yarnify.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Needle {

    //TABLE COLUMNS
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name="updated")
    private long mUpdateTime;

    //straight, double pointed, circular, hook
    @NonNull
    @ColumnInfo(name="type")
    private String mType;

    //knitting, crochet
    @NonNull
    @ColumnInfo(name="craft")
    private String mCraft;

    //Metric designation for hook size
    @NonNull
    @ColumnInfo(name="metric")
    private double mMetric;

    //Boolean true if crochet hook
    @ColumnInfo(name="isHook")
    private boolean mIsHook;

    //US Needle size number or Hook letter corresponding to this metric size, if one exists
    //The API has this separated into two separate attributes - US and Hook
    @ColumnInfo(name="us")
    private String mUs;

    @ColumnInfo(name="length")
    private int mLength;

    @ColumnInfo(name="brand")
    private String mBrand;

    //metal, plastic, wood, carbon fibre
    @ColumnInfo(name="material")
    private String mMaterial;

    @ColumnInfo(name="qty")
    private int mQty;

    //CONSTRUCTOR
    public Needle(@NonNull String type, String craft, double metric, boolean isHook,
                  String us, int length, String brand, String material, int qty){
        mType = type;
        mCraft = craft;
        mMetric = metric;
        mIsHook = isHook;
        mUs = us;
        mLength = length;
        mBrand = brand;
        mMaterial = material;
        mQty = qty;
        mUpdateTime = System.currentTimeMillis();
    }

    //GETTERS AND SETTERS
    public long getId() {
        return mId;
    }
    public void setId(long id) {
        this.mId = id;
    }

    public long getUpdateTime() { return  mUpdateTime; }
    public void setUpdateTime(long updateTime){ this.mUpdateTime = updateTime; }

    public String getType() { return mType; }
    public void setType(String type){ this.mType = type; }

    public String getCraft() { return mCraft; }
    public void setCraft(String craft){ this.mCraft = craft; }

    public double getMetric() { return mMetric; }
    public void setMetric(double metric) { this.mMetric = metric; }

    public boolean getIsHook() { return mIsHook; }
    public void setIsHook(boolean isHook) { this.mIsHook = isHook; }

    public String getUs() { return mUs; }
    public void setUS(String us){ this.mUs = us; }

    public int getLength() { return mLength; }
    public void setLength(int length) { this.mLength = length; }

    public String getBrand() { return mBrand; }
    public void setBrand(String brand){ this.mBrand = brand; }

    public String getMaterial() { return mMaterial; }
    public void setMaterial(String material){ this.mMaterial = material; }

    public int getQty() { return mQty; }
    public void setQty(int qty) { this.mQty = qty; }

}
