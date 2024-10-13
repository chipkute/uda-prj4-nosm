package com.example.demo.model;

import com.example.demo.model.persistence.User;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotEquals;

public class UserTest {
    @Test
    public void testEquals() {
        User user = new User();
        user.setId(0L);
        user.setUsername("test111");
        user.setPassword("password111");

        assertNotEquals(user, null);
        String str = "bob";
        assertNotEquals(str, user);

        User user2 = new User();
        user.setId(1L);
        user.setUsername("test112");
        user.setPassword("password112");

        assertNotEquals(user, user2);
    }
}
