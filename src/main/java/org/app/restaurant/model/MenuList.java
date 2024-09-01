package org.app.restaurant.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Entity
@Getter
@NoArgsConstructor
@Setter
@Table
public class MenuList {

    @Id
    private int id;

    private String item;

    private String qty;

    private double price;

    private String itemPngPath;

    @Override
    public String toString() {
        return "MenuList{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", qty='" + qty + '\'' +
                ", price=" + price +
                ", itemPngPath='" + itemPngPath + '\'' +
                '}';
    }
}
