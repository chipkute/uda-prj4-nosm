package com.example.demo.model;

import com.example.demo.model.persistence.Item;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    @Test
    public void testEquals() {
        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setPrice(new BigDecimal("2.99"));
        item.setDescription("A widget that is round");

        assertNotEquals(item, null);
        String str = "bob";
        assertNotEquals(str, item);

        Item item2 = new Item();
        item.setId(1L);
        item.setName("Square Widget");
        item.setPrice(new BigDecimal("1.99"));
        item.setDescription("A widget that is square");

        assertNotEquals(item, item2);
    }
}
