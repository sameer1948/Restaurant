package org.app.restaurant.controller;

import lombok.extern.slf4j.Slf4j;
import org.app.restaurant.dto.CustomUserAndDetails;
import org.app.restaurant.service.CustomUserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private CustomUserDetailsServices customUserDetailsServices;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/new-user") // Public End-Point; But after Integration It should be Private End point
    public CustomUserAndDetails addNewUser(@RequestBody CustomUserAndDetails customUserAndDetails) {
        customUserAndDetails.getCustomUser().setPassword(
                passwordEncoder.encode(customUserAndDetails.getCustomUser().getPassword()));
        return customUserDetailsServices.saveUser(customUserAndDetails);
    }

    @PostMapping("/new-users") // Public End-Point; But after Integration It should be Private End point
    public List<CustomUserAndDetails> addBulkNewUser(@RequestBody List<CustomUserAndDetails> customUserAndDetails) {
        // Encode passwords for all new users
        customUserAndDetails.forEach(details -> {
            String encodedPassword = passwordEncoder.encode(details.getCustomUser().getPassword());
            details.getCustomUser().setPassword(encodedPassword);
        });

        // Save the users
        return customUserDetailsServices.saveUserAll(customUserAndDetails);
    }

}
