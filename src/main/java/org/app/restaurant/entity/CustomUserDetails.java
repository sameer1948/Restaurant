package org.app.restaurant.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "custom_user_details")
public class CustomUserDetails {

    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    //@Column(name = "username", nullable = false)
    private String username;

    @Column
    private String firstName;

    @Column
    private String middleName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private String securityNumber;
}
