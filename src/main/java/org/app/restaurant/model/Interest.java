package org.app.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interest {

    private Integer interestId;
    private String interestType;
    private Integer value;
}
