/***************************************************************************************
 * Title: Mobile App Development with Android and Java
 * Author: Frank McCown, Associate Professor of Computer Science, Harding University
 * Date: 2018-2022
 * Code version: Java
 * Availability: https://www.zybooks.com/catalog/mobile-app-development/
 *
 ***************************************************************************************/package com.yarnify.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yarnify.model.User;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM User WHERE id = :id")
    LiveData<User> getUser(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addUser(User user);

    @Update
    void updateUser(User user);

}
