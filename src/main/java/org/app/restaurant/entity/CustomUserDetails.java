package org.app.restaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table
public class CustomUserDetails {

    @Id
    @NotNull
    private String username;

    @Column
    @NotNull
    @Size(min = 1, max = 50)
    private String firstName;

    @Column
    private String middleName;

    @Column
    @NotNull
    @Size(min = 1, max = 50)
    private String lastName;

    @Column
    @Email
    private String email;

    @Column
    //@Pattern(regexp = "^[0-9]{10}$", message = "Phone number should be 10 digits")
    private String phone;

    @Column
    @Size(max = 255)
    private String address;

    @Column
    //@Pattern(regexp = "^[A-Za-z0-9]{6,15}$", message = "Security number should be between 6 and 12 alphanumeric characters")
    private String securityNumber;
}
