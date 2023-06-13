/***************************************************************************************
 * Title: Mobile App Development with Android and Java
 * Author: Frank McCown, Associate Professor of Computer Science, Harding University
 * Date: 2018-2022
 * Code version: Java
 * Availability: https://www.zybooks.com/catalog/mobile-app-development/
 *
 ***************************************************************************************/

package com.yarnify.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Yarn {

    //TABLE COLUMNS
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name="updated")
    private long mUpdateTime;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "yarnWeight")
    private String mYarnWeight;

    //yards or meters
    @ColumnInfo(name = "lengthUnits")
    private String mLengthUnits;

    @ColumnInfo(name = "totalLength")
    private int mTotalLength;

    //grams or ounces
    @ColumnInfo(name = "weightUnits")
    private String mWeightUnits;

    @ColumnInfo(name = "totalWeight")
    private int mTotalWeight;

    @ColumnInfo(name = "colorFamily")
    private String mColorFamily;

    @ColumnInfo(name = "colorway")
    private String mColorway;

    @ColumnInfo(name = "dyeLot")
    private String mDyeLot;

    //CONSTRUCTOR
    //TODO: take out colorway - it should be with patterns, not yarn
    public Yarn(@NonNull String name, String yarnWeight, String lengthUnits,
                int totalLength, String weightUnits, int totalWeight, String colorFamily,
                String colorway, String dyeLot) {
        this.mName = name;
        this.mYarnWeight = yarnWeight;
        this.mLengthUnits = lengthUnits;
        this.mTotalLength = totalLength;
        this.mWeightUnits = weightUnits;
        this.mTotalWeight = totalWeight;
        this.mColorFamily = colorFamily;
        this.mColorway = colorway;
        this.mDyeLot = dyeLot;
        mUpdateTime = System.currentTimeMillis();
    }

    //GETTERS AND SETTERS
    public long getId() { return mId; }
    public void setId(long id) { this.mId = id; }

    public long getUpdateTime() { return mUpdateTime; }
    public void setUpdateTime(long mUpdateTime) { this.mUpdateTime = mUpdateTime; }

    public String getName() { return mName; }
    public void setName(String name) { this.mName = name; }

    public String getYarnWeight() { return mYarnWeight; }
    public void setYarnWeight(String yarnWeight) { this.mYarnWeight = yarnWeight; }

    public String getLengthUnits() { return mLengthUnits; }
    public void setLengthUnits(String lengthUnits) {this.mLengthUnits = lengthUnits; }

    public int getTotalLength() { return mTotalLength; }
    public void setTotalLength(int totalLength) { this.mTotalLength = totalLength; }

    public String getWeightUnits() { return mWeightUnits; }
    public void setWeightUnits(String weightUnits) { this.mWeightUnits = weightUnits; }

    public int getTotalWeight() { return mTotalWeight; }
    public void setTotalWeight(int totalWeight) { this.mTotalWeight = totalWeight; }

    public String getColorFamily() { return mColorFamily; }
    public void setColorFamily(String colorFamily) { this.mColorFamily = colorFamily; }

    public String getColorway() { return mColorway; }
    public void setColorway(String colorway) { this.mColorway = colorway; }

    public String getDyeLot() { return mDyeLot; }
    public void setDyeLot(String dyeLot) { this.mDyeLot = dyeLot; }
}
