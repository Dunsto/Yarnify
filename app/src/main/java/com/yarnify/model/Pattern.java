package com.yarnify.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pattern {

    //TABLE COLUMNS
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name="updated")
    private long mUpdateTime;

    @ColumnInfo(name="apiId")
    private long mApiId;

    @ColumnInfo(name="image")
    private String mImageResource;

    @NonNull
    @ColumnInfo(name="title")
    private String mTitle;

    @NonNull
    @ColumnInfo(name="creator")
    private String mCreator;

    @ColumnInfo(name="craft")
    private String mCraft;

    @ColumnInfo(name="patternURL")
    private String mURL;

    @ColumnInfo(name="minYardage")
    private int mMinYardage;

    @ColumnInfo(name="maxYardage")
    private int mMaxYardage;

    //TODO

    //FK: yarn_weight

    //FK: pattern_needle_sizes

    public Pattern() {
        // Empty constructor required by Room and Parcelable
    }

    // CONSTRUCTOR
    public Pattern(long apiId, String image, @NonNull String title, @NonNull String creator, String craft, String patternURL, int minYardage, int maxYardage) {
        this.mApiId = apiId;
        this.mImageResource = image;
        this.mTitle = title;
        this.mCreator = creator;
        this.mCraft = craft;
        this.mURL = patternURL;
        this.mMinYardage = minYardage;
        this.mMaxYardage = maxYardage;
        this.mUpdateTime = System.currentTimeMillis();
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

    public long getApiId() { return mApiId; }
    public void setApiId(long apiId) { this.mApiId = apiId; }

    public String getImageResource() { return mImageResource; }
    public void setImageResource(String imageResource) { this.mImageResource = imageResource; }

    public String getTitle() { return mTitle; }
    public void setTitle(String title) { this.mTitle = title; }

    public String getCreator() { return mCreator; }
    public void setCreator(String creator) { this.mCreator = creator; }

    public String getCraft() { return mCraft; }
    public void setCraft(String craft) { this.mCraft = craft; }

    public String getURL() { return mURL; }
    public void setURL(String URL) { this.mURL = URL; }

    public int getMinYardage() { return mMinYardage; }
    public void setMinYardage(int minYardage) { this.mMinYardage = minYardage; }

    public int getMaxYardage() { return mMaxYardage; }
    public void setMaxYardage(int maxYardage) { this.mMaxYardage = maxYardage; }

}