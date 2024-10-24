package org.app.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table(name = "MY_ORDER")
public class Order {

    @Id
    private long id;

    @Transient
    private List<MenuList> menuLists;

    private double amount;

    private Date orderDate;

    private String orderStatus;

    private String orderBy;

}
