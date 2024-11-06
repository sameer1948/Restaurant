package org.app.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table
public class CouponDetails {

    @Id
    private String couponId;

    private String memberName;

    private String message;

    private Timestamp timeStamp;
}
