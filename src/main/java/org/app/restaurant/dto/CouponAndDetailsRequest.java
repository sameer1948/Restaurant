package org.app.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.app.restaurant.entity.Coupon;
import org.app.restaurant.entity.CouponDetails;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouponAndDetailsRequest {

    private Coupon coupon;

    private CouponDetails couponDetails;

}
