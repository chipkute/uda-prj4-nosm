package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
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

public class OrderControllerTest {
    private OrderController orderController;
    private final UserRepository userRepository = mock(UserRepository.class);
    private final OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        com.example.demo.Utils.injectObjects(orderController, "userRepository", userRepository);
        com.example.demo.Utils.injectObjects(orderController, "orderRepository", orderRepository);
    }

    @Test
    public void submit() {
        String username = "test";

        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setId(0L);

        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setPrice(new BigDecimal("2.99"));
        item.setDescription("A widget that is round");

        Cart cart = new Cart();
        cart.setId(0L);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("2.99"));
        cart.setUser(user);
        user.setCart(cart);
        when(userRepository.findByUsername(username)).thenReturn(user);

        ResponseEntity<UserOrder> response =  orderController.submit(username);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        UserOrder retrievedUserOrder = response.getBody();
        assertNotNull(retrievedUserOrder);
        assertNotNull(retrievedUserOrder.getItems());
        assertNotNull(retrievedUserOrder.getTotal());
        assertNotNull(retrievedUserOrder.getUser());
    }

    @Test
    public void submitNullUser() {
        String username = "test";

        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setId(0L);

        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setPrice(new BigDecimal("2.99"));
        item.setDescription("A widget that is round");

        Cart cart = new Cart();
        cart.setId(0L);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("2.99"));
        cart.setUser(user);
        user.setCart(cart);
        when(userRepository.findByUsername(username)).thenReturn(null);

        ResponseEntity<UserOrder> response =  orderController.submit(username);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void getOrdersForUser() {
        String username = "test";

        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setId(0L);

        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setPrice(new BigDecimal("2.99"));
        item.setDescription("A widget that is round");

        Cart cart = new Cart();
        cart.setId(0L);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("2.99"));
        cart.setUser(user);
        user.setCart(cart);
        when(userRepository.findByUsername(username)).thenReturn(user);

        orderController.submit(username);

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(username);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        List<UserOrder> userOrders = responseEntity.getBody();
        assertNotNull(userOrders);
        assertEquals(0, userOrders.size());
    }

    @Test
    public void getOrdersForUserNullUser() {
        String username = "test";
        User user = new User();
        user.setUsername(username);
        user.setPassword("password");
        user.setId(0L);

        Item item = new Item();
        item.setId(0L);
        item.setName("Round Widget");
        item.setPrice(new BigDecimal("2.99"));
        item.setDescription("A widget that is round");

        Cart cart = new Cart();
        cart.setId(0L);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        cart.setItems(itemList);
        cart.setTotal(new BigDecimal("2.99"));
        cart.setUser(user);
        user.setCart(cart);
        when(userRepository.findByUsername(username)).thenReturn(null);

        orderController.submit(username);

        ResponseEntity<List<UserOrder>> responseEntity = orderController.getOrdersForUser(username);

        assertNotNull(responseEntity);
        assertEquals(404, responseEntity.getStatusCodeValue());
    }
}
