package org.app.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table
public class MenuList {

    @Id
    @GeneratedValue(generator = "menuList-id-generator")
    @GenericGenerator(name = "menuList-id-generator", strategy = "org.app.restaurant.generator.MenuListIdGenerator")
    private String id;

    private String item;

    private String description;

    private int quantity;

    private double price;

    private String imagePath;
}