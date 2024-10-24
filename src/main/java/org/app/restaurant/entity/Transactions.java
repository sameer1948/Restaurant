package org.app.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transactions {

    private Long transactionId;
    private Long accountNumber;
    private Double transAmount;
    private String transStatus;
    private Date transStartTime;
    private Date transCompleteTime;
    private String transChannel; // WEB, MOBILE, UPI

}
