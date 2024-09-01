package org.app.restaurant.model;

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
@Table(name = "BRANCH")
public class Branch {

    @Id
    @Column
    private Integer branchId;

    @Column
    private String branchName;

    @Column
    private String branchLocation;

}
