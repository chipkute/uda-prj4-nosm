package com.example.demo.controllers;

import com.example.demo.Utils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemController itemController;
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        Utils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    @Test
    public void getItems() {
        Item item1 = new Item();
        item1.setId(0L);
        item1.setName("Round Widget");
        item1.setPrice(new BigDecimal("2.99"));
        item1.setDescription("A widget that is round");

        Item item2 = new Item();
        item2.setId(1L);
        item2.setName("Square Widget");
        item2.setPrice(new BigDecimal("1.99"));
        item2.setDescription("A widget that is square");

        List<Item> items = new ArrayList<>(2);
        items.add(item1);
        items.add(item2);
        when(itemRepository.findAll()).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> retrievedItems = response.getBody();
        assertNotNull(retrievedItems);
        assertEquals(2, retrievedItems.size());
        assertEquals(item1, retrievedItems.get(0));
        assertEquals(item2, retrievedItems.get(1));
    }

    @Test
    public void getItemById() {
        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setPrice(new BigDecimal("2.99"));
        item.setDescription("A widget that is round");

        when(itemRepository.findById(0L)).thenReturn(java.util.Optional.of(item));

        ResponseEntity<Item> response = itemController.getItemById(0L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        Item retrievedItem = response.getBody();
        assertEquals(item, retrievedItem);
        assertNotNull(retrievedItem);
        assertEquals(item.getName(), retrievedItem.getName());
        assertEquals(item.getId(), retrievedItem.getId());
        assertEquals(item.getDescription(), retrievedItem.getDescription());
    }

    @Test
    public void getItemsByName() {
        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setPrice(new BigDecimal("2.99"));
        item.setDescription("A widget that is round");

        List<Item> items = new ArrayList<>(2);
        items.add(item);
        when(itemRepository.findByName("Round Widget")).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Round Widget");

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        List<Item> retrievedItems = response.getBody();
        assertNotNull(retrievedItems);
        assertEquals(1, retrievedItems.size());
        assertEquals(item, retrievedItems.get(0));
    }
}
