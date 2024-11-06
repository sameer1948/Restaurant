package org.app.restaurant.entity;

import jakarta.persistence.*;
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
public class Tax {

    @Id
    @GeneratedValue(generator = "tax-id-generator")
    @GenericGenerator(name = "tax-id-generator", strategy = "org.app.restaurant.generator.TaxIdGenerator")
    private String taxId;

    private String taxType;

    @Column(name = "tax_value")
    private double value;

    private boolean status;
}
