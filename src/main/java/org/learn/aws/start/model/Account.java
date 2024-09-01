package org.learn.aws.start.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class Account {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column//(name = "ACCOUNT_NUMBER", nullable = false)
    private Long accountNumber;
    @Column
    private Long userId;

    @Column
    private Integer accountTypeId;

    @Column
    private Integer branchId;

    @Column
    private String accountHolderName;

    @Column
    private Double balance;

    @Column
    private String status;
}
