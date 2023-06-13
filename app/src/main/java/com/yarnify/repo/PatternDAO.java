package com.yarnify.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yarnify.model.Pattern;

import java.util.List;

@Dao
public interface PatternDAO {

    @Query("SELECT * FROM Pattern WHERE id = :id")
    LiveData<Pattern> getPattern(long id);

    @Query("SELECT * FROM Pattern")
    LiveData<List<Pattern>> getPatterns();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long addPattern(Pattern pattern);

    @Update
    void updatePattern(Pattern pattern);

    @Delete
    void deletePattern(Pattern pattern);

    @Query("DELETE FROM Pattern WHERE id = :id")
    void deletePattern(long id);

    // delete all patterns
    @Query("DELETE FROM Pattern")
    void deleteAllPatterns();

    // checks if a pattern with the same title and creator already exists in the database before inserting
    @Query("SELECT COUNT(*) FROM Pattern WHERE title = :title AND creator = :creator")
    int countPatterns(String title, String creator);

    // checks if a pattern with the same title and creator already exists in the database to update boolean and button in activity
    @Query("SELECT COUNT(*) FROM Pattern WHERE title = :title AND creator = :creator")
    LiveData<Integer> countPatterns2(String title, String creator);

    // Get pattern ID by matching title and creator (for deleting purposes)
    @Query("SELECT id FROM Pattern WHERE title = :title AND creator = :creator LIMIT 1")
    long getPatternIdByTitleAndCreator(String title, String creator);
}
