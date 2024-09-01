package org.learn.aws.start.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class AccountType {

    @Id
    @Column
    private Integer accountTypeId;

    @Column
    private String accountTypeName; // SAVINGS, CURRENT, RECURRING, FIXED

    @Column
    private Integer interestId;
}
