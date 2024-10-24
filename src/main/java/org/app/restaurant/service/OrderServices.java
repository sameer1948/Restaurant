package org.app.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.restaurant.constatnts.OrderStatus;
import org.app.restaurant.entity.MenuList;
import org.app.restaurant.entity.Order;
import org.app.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderServices {

    private final OrderRepository orderRepository;

    public Order newOrder(Order order) {
        if(order == null || order.getMenuLists().isEmpty()) {
            throw new NullPointerException("No Object Found");
        }

        double totalAmount = order.getMenuLists().stream()
                .map(MenuList::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
       long orderId = System.currentTimeMillis();

       order.setId(orderId);
       order.setAmount(totalAmount);
       order.setOrderStatus(OrderStatus.PENDING.name());
       order.setOrderDate(new Date(orderId));

        log.info("order -> {}" , order);
        return orderRepository.save(order);
    }
}
