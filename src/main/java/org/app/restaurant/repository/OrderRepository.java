package org.app.restaurant.repository;

import org.app.restaurant.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    @Query("SELECT o FROM Order o WHERE o.orderBy = :orderBy AND o.orderDate = :orderDate and o.totalPrice = :totalPrice")
    Optional<Order> findByOrderByAndOrderDate(@Param("orderBy") String orderBy, @Param("orderDate") Timestamp orderDate, @Param("totalPrice") double totalPrice);

    @Query("SELECT o FROM Order o WHERE o.totalPrice = :totalPrice AND o.orderBy = :orderBy")
    Optional<Order> findByOrderDetails(@Param("totalPrice") double totalPrice, @Param("orderBy") String orderBy);

    @Modifying
    @Query("UPDATE Order o SET o.orderStatus = :orderStatus WHERE o.id = :orderId")
    void updateOrderStatus(@Param("orderStatus") String orderStatus, @Param("orderId") String orderId);

}

