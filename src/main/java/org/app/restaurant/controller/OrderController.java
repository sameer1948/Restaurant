package org.app.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.app.restaurant.dto.OrderAndDetailsRequest;
import org.app.restaurant.entity.Order;
import org.app.restaurant.service.OrderServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private ResponseEntity<Order> newOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderServices.createOrder(order));
    }

    @PostMapping("/new-orders")
    private ResponseEntity<List<Order>> newOrderAll(@RequestBody List<Order> orders) {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderServices.createOrders(orders));
    }


    @GetMapping("/fetch-orders")
    private ResponseEntity<List<Order>> fetchOrderAll() {
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(orderServices.fetchOrders());
    }

    /**
     * Fetches all orders from the database with pagination.
     *
     * @param page the page number (default value = 0)
     * @param size the page size (default value = 10)
     * @return a paginated response of orders
     */
    @GetMapping("/fetch-pages")
    public ResponseEntity<Page<Order>> fetchOrdersWithPagination(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {

        // Create a Pageable object using the page number and size parameters
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated orders
        Page<Order> ordersPage = orderServices.fetchAllOrders(pageable);

        // Return the response with the paginated list of orders
        return ResponseEntity.status(HttpStatus.OK).body(ordersPage);
    }
}
