package com.food.onlineordering.controller;

import com.food.onlineordering.model.CartItem;
import com.food.onlineordering.model.Order;
import com.food.onlineordering.model.User;
import com.food.onlineordering.request.AddCartItemRequest;
import com.food.onlineordering.request.OrderRequest;
import com.food.onlineordering.service.OrderService;
import com.food.onlineordering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request,
                                             @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.createOrder(request, user);

        return new ResponseEntity<>(order, HttpStatus.OK);

    }


    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Order> order = orderService.getUsersOrder(user.getId());

        return new ResponseEntity<>(order, HttpStatus.OK);

    }
}
