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
public class TaxDetails {

    @Id
    private String taxId;

    private String memberName;

    private String message;

    private Timestamp timeStamp;
}
