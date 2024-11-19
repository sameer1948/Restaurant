package org.app.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.app.restaurant.entity.Order;
import org.app.restaurant.service.OrderServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderServices orderServices;

    @GetMapping("/")
    public String testOrders() {
        return "Order Controller";
    }

    @PostMapping("/new-order")
    public ResponseEntity<Order> newOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderServices.createOrder(order));
    }

    @PostMapping("/new-orders")
    public ResponseEntity<List<Order>> newOrderAll(@RequestBody List<Order> orders) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderServices.createOrders(orders));
    }

    @GetMapping("/fetch-order/{orderId}")
    public ResponseEntity<Order> fetchOrder(@PathVariable("orderId") String orderId) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderServices.fetchOrder(orderId));
    }


    @GetMapping("/fetch-orders")
    public ResponseEntity<List<Order>> fetchOrderAll() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderServices.fetchOrders());
    }


    // PATCH mapping to update an order
    @PatchMapping("/update-order/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") String orderId, @RequestBody Order order) {
        Order updatedOrder = orderServices.updateOrder(orderId, order);
        if (updatedOrder != null) {
            // Return the updated order with a 200 OK status
            return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
        } else {
            // Return 404 Not Found if the order does not exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
