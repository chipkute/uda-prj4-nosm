package com.example.demo.model;

import com.example.demo.model.persistence.UserOrder;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserOrderTest {
    @Test
    public void getId() {
        UserOrder userOrder = new UserOrder();
        userOrder.setId(2L);
        assertEquals(2L, userOrder.getId());
    }
}
