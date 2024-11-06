package org.app.restaurant.repository;

import org.app.restaurant.entity.CouponDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponDetailsRepository extends JpaRepository<CouponDetails, String> {
    List<CouponDetails> findByCouponId(String couponId);
}
