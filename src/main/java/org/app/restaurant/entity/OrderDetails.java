package org.app.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Builder
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "MY_ORDER_DETAILS")
public class OrderDetails {

    @Id
    private String id;

    @ManyToMany
    @JoinTable(
            name = "order_menu_list",
            joinColumns = @JoinColumn(name = "order_details_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_list_id", referencedColumnName = "id")
    )
    private List<MenuList> menuLists;

    @ManyToMany
    @JoinTable(
            name = "order_tax",
            joinColumns = @JoinColumn(name = "order_details_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tax_id", referencedColumnName = "taxId")
    )
    private List<Tax> taxList;

    @ManyToMany
    @JoinTable(
            name = "order_coupon",
            joinColumns = @JoinColumn(name = "order_details_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id", referencedColumnName = "couponId")
    )
    private List<Coupon> coupons;

    @OneToOne(mappedBy = "orderDetails")
    private Order order;
}
