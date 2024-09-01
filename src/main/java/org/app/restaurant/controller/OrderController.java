package org.app.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.app.restaurant.model.Order;
import org.app.restaurant.service.OrderServices;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {

    private final OrderServices orderServices;

    @GetMapping("/")
    public String testOrders() {
        return "Order Controller";
    }

    @PostMapping("/new-order")
    private ResponseEntity<Order> newOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderServices.newOrder(order));
    }
}
