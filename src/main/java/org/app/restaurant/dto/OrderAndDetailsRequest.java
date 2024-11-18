package org.app.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.app.restaurant.entity.Order;
import org.app.restaurant.entity.OrderDetails;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderAndDetailsRequest {

    private Order order;

    private OrderDetails orderDetails;

}