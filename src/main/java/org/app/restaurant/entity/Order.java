package org.app.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "MY_ORDER")
public class Order {

    @Id
    @GeneratedValue(generator = "order-id-generator")
    @GenericGenerator(name = "order-id-generator", strategy = "org.app.restaurant.generator.OrderIdGenerator")
    private String id;

    private double totalPrice;
    private Timestamp orderDate;
    private String orderStatus;
    private String orderBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_details_id", referencedColumnName = "id")
    private OrderDetails orderDetails;

    /*cascading operations (e.g., persisting or deleting the OrderDetails when an Order is saved or deleted),
    you can add cascade = CascadeType.ALL to the @OneToOne annotation on the Order entity.*/
}
