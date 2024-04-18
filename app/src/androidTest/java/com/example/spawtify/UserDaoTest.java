package com.example.spawtify;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    private UserDAO userDAO;
    private SpawtifyDatabase db;

    @Before
    public void createDatabase(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, SpawtifyDatabase.class).build();
        userDAO = db.getUserDAO();
    }

    @After
    public void closeDb(){
        db.close();
    }

    @Test
    public void insertTest(){
        //  Creates new user for us to pass in
        User testUser = new User("testUser", "testPassword");
        //  Insert user into database
        userDAO.insert(testUser);
        //  Get list of users (the one we passed in) from database
        List<User> allUsers = userDAO.getAllUsers();
        //  Assert that list size is equal to 1
        assertEquals(1, allUsers.size());
        //  Assert that usernames are equal
        assertEquals(testUser.getUsername(), allUsers.get(0).getUsername());
    }

    @Test
    public void updateTest(){
        //  Creates new user for us to pass in
        User testUser = new User("testUser", "testPassword");
        //  Inserts user into database
        userDAO.insert(testUser);
        //  Retrieves list of users (the one we passed in) from the database
        List<User> allUsers = userDAO.getAllUsers();
        //  Assert that list size is equal to 1
        assertEquals(1, allUsers.size());
        //  Assert that usernames are equal
        assertEquals(testUser.getUsername(), allUsers.get(0).getUsername());
        //  Set local user's username to "Mr Pojo"
        allUsers.get(0).setUsername("Mr Pojo");
        //  Send in user object with modified username to database
        userDAO.update(allUsers.get(0));
        //  Retrieve users from database (our user with updated username)
        allUsers = userDAO.getAllUsers();
        //  Assert that user's new username is Mr Pojo
        assertEquals("Mr Pojo", allUsers.get(0).getUsername());
    }

    @Test
    public void deleteTest(){
        User testUser = new User("testUser", "testPassword");
        userDAO.insert(testUser);
        //  Retrieves list of users (the one we passed in) from the database
        List<User> allUsers = userDAO.getAllUsers();
        //  Assert that list size is equal to 1
        assertEquals(1, allUsers.size());
        //  Assert that usernames are equal
        assertEquals(testUser.getUsername(), allUsers.get(0).getUsername());
        //  Delete user that we sent into the database
        //  **DELETE USER BY SENDING IN TESTUSER DOES NOT WORK BECAUSE THE IDS ARE DIFFERENT
        userDAO.delete(allUsers.get(0));
        //  Retrieve all users (should be none inside since we deleted the only one)
        allUsers = userDAO.getAllUsers();
        //  Assert that all users list size is 0
        assertEquals(0, allUsers.size());
    }
}
