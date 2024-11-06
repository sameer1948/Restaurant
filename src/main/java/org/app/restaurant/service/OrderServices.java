package org.app.restaurant.service;

import org.app.restaurant.constatnts.OrderStatus;
import org.app.restaurant.entity.*;
import org.app.restaurant.exception.CouponNotFoundException;
import org.app.restaurant.exception.InvalidTotalPriceException;
import org.app.restaurant.exception.MenuItemNotFoundException;
import org.app.restaurant.exception.TaxNotFoundException;
import org.app.restaurant.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.app.restaurant.utils.DateUtility.getCurrentDate;

@Service
public class OrderServices {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private MenuListRepository menuListRepository;

    @Autowired
    private TaxRepository taxRepository;

    /**
     * Creates a new order after validating all relevant details.
     *
     * @param orderRequest the order object to be created
     * @return the created order
     */
    public Order createOrder(Order orderRequest) {

        if (orderRequest.getOrderDetails().getCoupons() != null && !orderRequest.getOrderDetails().getCoupons().isEmpty()) {
            for (Coupon coupon : orderRequest.getOrderDetails().getCoupons()) {
                validateCoupon(coupon);
            }
        }

        if (orderRequest.getOrderDetails().getMenuLists() != null && !orderRequest.getOrderDetails().getMenuLists().isEmpty()) {
            for (MenuList menuItem : orderRequest.getOrderDetails().getMenuLists()) {
                validateMenuItem(menuItem);
            }
        }

        if (orderRequest.getOrderDetails().getTaxList() != null && !orderRequest.getOrderDetails().getTaxList().isEmpty()) {
            for (Tax tax : orderRequest.getOrderDetails().getTaxList()) {
                validateTax(tax);
            }
        }

        validateOrderTotalPrice(orderRequest);

        Order savedOrder = orderRepository.save(Order.builder()
                .totalPrice(orderRequest.getTotalPrice())
                .orderDate(getCurrentDate())
                .orderStatus(OrderStatus.COMPLETED.name())
                .orderBy(orderRequest.getOrderBy())
                .build());

        OrderDetails savedOrderDetails = orderDetailsRepository.save(OrderDetails.builder()
                .id(savedOrder.getId())
                .coupons(orderRequest.getOrderDetails().getCoupons())
                .menuLists(orderRequest.getOrderDetails().getMenuLists())
                .taxList(orderRequest.getOrderDetails().getTaxList())
                .build());

        savedOrder.setOrderDetails(savedOrderDetails);

        return savedOrder;

    }

    public List<Order> createOrders(List<Order> orders) {

        List<Order> orderList = new ArrayList<>();
        orders.forEach(order -> {
            System.out.println(order.toString());
            orderList.add(createOrder(order));
        });

        return orderList;
    }


    public List<Order> fetchOrders() {

        return orderRepository.findAll();
    }

    /**
     * Fetches all orders with pagination.
     *
     * @param pageable the pageable object containing pagination information
     * @return a paginated list of orders
     */
    public Page<Order> fetchAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    /**
     * Validates if the coupon exists in the database.
     *
     * @param coupon the coupon to validate
     */
    private void validateCoupon(Coupon coupon) {
        Optional<Coupon> existingCoupon = couponRepository.findById(coupon.getCouponId());
        if (existingCoupon.isEmpty()) {
            throw new CouponNotFoundException("Coupon " + coupon.getCouponId() + " not found.");
        }
    }

    /**
     * Validates if the menu item exists in the database.
     *
     * @param menuItem the menu item to validate
     */
    private void validateMenuItem(MenuList menuItem) {
        Optional<MenuList> existingMenuItem = menuListRepository.findById(menuItem.getId());
        if (existingMenuItem.isEmpty()) {
            throw new MenuItemNotFoundException("Menu item with ID " + menuItem.getId() + " not found.");
        }
    }

    /**
     * Validates if the tax details are valid.
     *
     * @param tax the tax details to validate
     */
    private void validateTax(Tax tax) {
        Optional<Tax> existingTax = taxRepository.findById(tax.getTaxId());
        if (existingTax.isEmpty()) {
            throw new TaxNotFoundException("Tax with ID " + tax.getTaxId() + " not found.");
        }
    }

    /**
     * Validates if the order total price is consistent with the sum of the menu item prices and taxes.
     *
     * @param order the order to validate
     */
    private void validateOrderTotalPrice(Order order) {
        double calculatedTotalPrice = 0.0;

        // Calculate the total price of the menu items
        for (MenuList menuItem : order.getOrderDetails().getMenuLists()) {
            calculatedTotalPrice += menuItem.getPrice() * menuItem.getQuantity();
        }

        // Add the total tax to the calculated price
        for (Tax tax : order.getOrderDetails().getTaxList()) {
            calculatedTotalPrice += tax.getValue();
        }

        // Apply coupon discount if applicable
        for (Coupon coupon : order.getOrderDetails().getCoupons()) {
            if (coupon.getIsPercentage()) {
                calculatedTotalPrice -= (calculatedTotalPrice * (coupon.getPercentage() / 100));
            } else if (coupon.getIsAmount()) {
                calculatedTotalPrice -= coupon.getAmount();
            }
        }

        // Check if the calculated total matches the total price in the order
        if (Math.abs(calculatedTotalPrice - order.getTotalPrice()) > 0.01) {
            throw new InvalidTotalPriceException("Total price mismatch. Expected: " + calculatedTotalPrice + ", but got: " + order.getTotalPrice());
        }
    }



}
