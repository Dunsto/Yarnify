package com.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.yarnify.model.User;
import com.yarnify.repo.LocalDatabase;
import com.yarnify.repo.UserDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class UserDAOTest {

    private LocalDatabase database;
    private UserDAO userDAO;

    @Before
    public void setup() {
        // Create an in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                LocalDatabase.class
        ).build();

        // Get the UserDAO instance
        userDAO = database.userDAO();
    }

    @After
    public void cleanup() {
        // Close the in-memory database after each test
        database.close();
    }

    @Test
    public void testGetUser() throws InterruptedException {
        // Create a user and insert it into the database
        User user = new User("JohnDoe", "johndoe123");
        long userId = userDAO.addUser(user);

        // Get the LiveData<User> from the database
        LiveData<User> liveData = userDAO.getUser(userId);

        // Retrieve the User object using LiveDataTestUtil
        User retrievedUser = LiveDataTestUtil.getValue(liveData);

        // Assert that the retrieved User is not null
        assertNotNull(retrievedUser);

        // Assert that the retrieved User has the same username as the one we inserted
        assertEquals("JohnDoe", retrievedUser.getUsername());

        // Assert that the retrieved User has the same Ravelry username as the one we inserted
        assertEquals("johndoe123", retrievedUser.getRavelryUsername());
    }
}

