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
public class User {

    //TABLE COLUMNS
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "username")
    private String mUsername;

    @ColumnInfo(name="updated")
    private long mUpdateTime;

    @ColumnInfo(name="ravelryUsername")
    private String mRavelryUsername;

    //CONSTRUCTOR
    public User(@NonNull String username, String ravelryUsername){
        mUsername = username;
        mRavelryUsername = ravelryUsername;
        mUpdateTime = System.currentTimeMillis();
    }

    //GETTERS AND SETTERS
    public long getId() {
        return mId;
    }
    public void setId(long id) {
        this.mId = id;
    }

    public String getUsername() {
        return mUsername;
    }
    public void setUsername(String username) {
        this.mUsername = username;
    }

    public long getUpdateTime() { return  mUpdateTime; }
    public void setUpdateTime(long updateTime){ this.mUpdateTime = updateTime; }

    public String getRavelryUsername() { return mRavelryUsername; }
    public void setRavelryUsername(String ravelryUsername) {
        this.mRavelryUsername = ravelryUsername;
    }
}
