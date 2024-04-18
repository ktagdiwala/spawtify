package com.example.spawtify.Database.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** UserTest
 * Runs Unit tests for User entity object
 * @author Krishna Tagdiwala
 * @since 04-17-2024
 */

public class UserTest {

    User user;

    @Before
    public void setUp() {
        user = new User("cat", "meowdy");
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void isAdmin() {
        assertFalse(user.isAdmin());
    }

    @Test
    public void setAdmin() {
        assertFalse(user.isAdmin());
        user.setAdmin(true);
        assertTrue(user.isAdmin());
    }

    @Test
    public void getUsername() {
        assertEquals("cat", user.getUsername());
    }

    @Test
    public void setUsername() {
        assertNotEquals("dog", user.getUsername());
        user.setUsername("dog");
        assertEquals("dog", user.getUsername());
    }

    @Test
    public void getPassword() {
        assertEquals("meowdy", user.getPassword());
    }

    @Test
    public void setPassword() {
        assertNotEquals("pawsome", user.getPassword());
        user.setPassword("pawsome");
        assertEquals("pawsome", user.getPassword());
    }
}