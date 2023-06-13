/***************************************************************************************
 * Title: Mobile App Development with Android and Java
 * Author: Frank McCown, Associate Professor of Computer Science, Harding University
 * Date: 2018-2022
 * Code version: Java
 * Availability: https://www.zybooks.com/catalog/mobile-app-development/
 *
 ***************************************************************************************/

package com.yarnify.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.yarnify.model.Needle;
import com.yarnify.model.User;
import com.yarnify.model.Yarn;
import com.yarnify.model.Pattern;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Repository {
    private static Repository mRepository;
    private final UserDAO mUserDAO;
    private final YarnDAO mYarnDAO;
    private final NeedleDAO mNeedleDAO;
    private final PatternDAO mPatternDAO;
    private LiveData<Integer> patternCountLiveData;

    //ExecutorService creates background threads.
    //LiveData automatically uses a thread, so it will be used to insert/update/delete
    private static final ExecutorService mDatabaseExecutor =
            Executors.newFixedThreadPool(4);

    public static Repository getInstance(Context context) {
        if (mRepository == null) {
            mRepository = new Repository(context);
        }
        return mRepository;
    }

    private Repository(Context context) {
        LocalDatabase database = Room.databaseBuilder(context, LocalDatabase.class, "yarnify.db")
                .build();

        mUserDAO = database.userDAO();
        mYarnDAO = database.yarnDAO();
        mNeedleDAO = database.needleDAO();
        mPatternDAO = database.patternDAO();
    }

    //User Table: addUser, getUser, updateUser
    public void addUser(User user) {
        mDatabaseExecutor.execute(() -> {
            long userId = mUserDAO.addUser(user);
            user.setId(userId);
        });
    }

    public LiveData<User> getUser(long userId) {
        return mUserDAO.getUser(userId);
    }

    public void updateUser(User user) {
        mDatabaseExecutor.execute(() -> {
            mUserDAO.updateUser(user);
        });
    }

    //Yarn Table: getYarn, getYarns, addYarn, updateYarn deleteYarn
    //TODO: getYarnsOrderBy(String orderChoice)
    public LiveData<Yarn> getYarn(long yarnId) {
        return mYarnDAO.getYarn(yarnId);
    }

    public LiveData<List<Yarn>> getYarns() {
        return mYarnDAO.getYarns();
    }

    public void addYarn(Yarn yarn) {
        mDatabaseExecutor.execute(() -> {
            mYarnDAO.addYarn(yarn);
        });
    }

    public void updateYarn(Yarn yarn) {
        mDatabaseExecutor.execute(() -> {
            mYarnDAO.updateYarn(yarn);
        });
    }

    public void deleteYarn(Yarn yarn) {
        mDatabaseExecutor.execute(() -> {
            mYarnDAO.deleteYarn(yarn);
        });
    }

    public void deleteYarn(long id) {
        mDatabaseExecutor.execute(() -> {
            mYarnDAO.deleteYarn(id);
        });
    }

    //Needle Table: getNeedle, getNeedles, addNeedle, updateNeedle deleteNeedle
    public LiveData<Needle> getNeedle(long needleId) {
        return mNeedleDAO.getNeedle(needleId);
    }

    public LiveData<List<Needle>> getNeedles() {
        return mNeedleDAO.getNeedles();
    }

    public void addNeedle(Needle needle) {
        mDatabaseExecutor.execute(() -> {
            mNeedleDAO.addNeedle(needle);
        });
    }

    public void updateNeedle(Needle needle) {
        mDatabaseExecutor.execute(() -> {
            mNeedleDAO.updateNeedle(needle);
        });
    }

    public void deleteNeedle(Needle needle) {
        mDatabaseExecutor.execute(() -> {
            mNeedleDAO.deleteNeedle(needle);
        });
    }

    public void deleteNeedle(long id) {
        mDatabaseExecutor.execute(() -> {
            mNeedleDAO.deleteNeedle(id);
        });
    }

    //Pattern Table: getPattern, getPatterns, addPattern, updatePattern deletePattern

    public LiveData<Pattern> getPattern(long patternId) {
        return mPatternDAO.getPattern(patternId);
    }

    public LiveData<List<Pattern>> getPatterns() {
        return mPatternDAO.getPatterns();
    }

    public void addPattern(Pattern pattern) {
        mDatabaseExecutor.execute(() -> { //logic for checking if the pattern already exists before inserting
            int count = mPatternDAO.countPatterns(pattern.getTitle(), pattern.getCreator());
            if (count == 0) {
                mPatternDAO.addPattern(pattern);
            }
        });
    }

    public void updatePattern(Pattern pattern) {
        mDatabaseExecutor.execute(() -> {
            mPatternDAO.updatePattern(pattern);
        });
    }

    public void deletePattern(Pattern pattern) {
        mDatabaseExecutor.execute(() -> {
            mPatternDAO.deletePattern(pattern);
        });
    }

    public void deletePattern(long id) {
        mDatabaseExecutor.execute(() -> {
            mPatternDAO.deletePattern(id);
        });
    }

    public void deleteAllPatterns() {
        mDatabaseExecutor.execute(() -> {
            mPatternDAO.deleteAllPatterns();
        });
    }

    public LiveData<Integer> getPatternCountLiveData(String title, String creator) {
        patternCountLiveData = mPatternDAO.countPatterns2(title, creator);
        return patternCountLiveData;
    }

    /*submits the query to the mDatabaseExecutor as a Callable task using submit(). The submit()
    method returns a Future object that represents the result of the query. Then, the method waits
    for the query result by calling future.get(), which blocks until the result is available. This
    is done within a try-catch block to handle any potential exceptions that may occur.*/
    public long getPatternIdByTitleAndCreator(String title, String creator) {
        Future<Long> future = mDatabaseExecutor.submit(() ->
                mPatternDAO.getPatternIdByTitleAndCreator(title, creator)
        );

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return 0; // Return a default value or handle the error as needed
        }
    }
}
