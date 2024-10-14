package org.app.restaurant.controller;

import lombok.extern.slf4j.Slf4j;
import org.app.restaurant.dto.NewCustomUserRequest;
import org.app.restaurant.service.CustomUserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private CustomUserDetailsServices customUserDetailsServices;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/new-user") // Public End-Point; But after Integration It should be Private End point
    public NewCustomUserRequest addNewUser(@RequestBody NewCustomUserRequest newCustomUserRequest) {
        newCustomUserRequest.getCustomUser().setPassword(
                passwordEncoder.encode(newCustomUserRequest.getCustomUser().getPassword()));
        return customUserDetailsServices.saveUser(newCustomUserRequest);
    }
}
