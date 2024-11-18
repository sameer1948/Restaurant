package org.app.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table
public class Coupon {

    @Id
    @GeneratedValue(generator = "coupon-id-generator")
    @GenericGenerator(name = "coupon-id-generator", strategy = "org.app.restaurant.generator.CouponIdGenerator")
    private String couponId;

    private String couponName;
    private String description;

    private Boolean isAmount;  // This should be a boolean field
    private double amount;

    private Boolean isPercentage;  // This should be a boolean field
    private double percentage;

    private double maxDiscountAmount;
    private double minOrderAmount;
    private Boolean status;  // This should be a boolean field

    private Timestamp startDate;
    private Timestamp endDate;
}