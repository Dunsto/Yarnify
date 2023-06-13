/***************************************************************************************
 * Title: Mobile App Development with Android and Java
 * Author: Frank McCown, Associate Professor of Computer Science, Harding University
 * Date: 2018-2022
 * Code version: Java
 * Availability: https://www.zybooks.com/catalog/mobile-app-development/
 *
 ***************************************************************************************/

package com.yarnify.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yarnify.model.Yarn;

import java.util.List;

@Dao
public interface YarnDAO {

    @Query("SELECT * FROM Yarn WHERE id = :id")
    LiveData<Yarn> getYarn(long id);

    @Query("SELECT * FROM Yarn")
    LiveData<List<Yarn>> getYarns();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addYarn(Yarn yarn);

    @Update
    void updateYarn(Yarn yarn);

    @Delete
    void deleteYarn(Yarn yarn);

    @Query("DELETE FROM Yarn WHERE id = :id")
    void deleteYarn(long id);
}
