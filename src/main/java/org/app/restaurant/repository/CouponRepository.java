package org.app.restaurant.repository;

import org.app.restaurant.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {
    boolean existsByCouponName(String couponName);

    Coupon findByCouponName(String couponName);
}
