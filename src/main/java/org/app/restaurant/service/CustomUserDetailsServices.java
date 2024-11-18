package org.app.restaurant.service;

import lombok.extern.slf4j.Slf4j;
import org.app.restaurant.dto.CustomUserAndDetails;
import org.app.restaurant.entity.*;
import org.app.restaurant.exception.UserAlreadyExistsException;
import org.app.restaurant.exception.UserNotFoundException;
import org.app.restaurant.repository.CustomUserDetailsRepository;
import org.app.restaurant.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailsServices implements UserDetailsService {

    @Autowired
    private CustomUserRepository customUserRepository;
    @Autowired
    private CustomUserDetailsRepository customUserDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User : " + username + " Not Found ...!"));
    }

    public CustomUserAndDetails saveUser(CustomUserAndDetails customUserAndDetails) throws UserAlreadyExistsException { // Add Exception Handler
        if (customUserRepository.findById(customUserAndDetails.getCustomUser().getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User : " + customUserAndDetails.getCustomUser().getUsername() + " Already Existing...!");
        }

        customUserAndDetails.getCustomUserDetails().setUsername(customUserAndDetails.getCustomUser().getUsername());
        CustomUserDetails customUserDetails = customUserDetailsRepository.save(customUserAndDetails.getCustomUserDetails());

        if(!customUserDetails.getUsername().isEmpty()) {
            return CustomUserAndDetails.builder()
                     .customUser(customUserRepository.save(customUserAndDetails.getCustomUser()))
                     .customUserDetails(customUserDetails).build();
        }
        throw new RuntimeException("Unable To Save User : " + customUserAndDetails.getCustomUser().getUsername());
    }

    public List<CustomUserAndDetails> saveUserAll(List<CustomUserAndDetails> customUserAndDetails) throws UserAlreadyExistsException {
        List<CustomUserAndDetails> savedUsers = new ArrayList<>();

        for (CustomUserAndDetails details : customUserAndDetails) {
            // Check if the user already exists
            if (customUserRepository.findById(details.getCustomUser().getUsername()).isPresent()) {
                throw new UserAlreadyExistsException("User: " + details.getCustomUser().getUsername() + " already exists!");
            }

            // Set username for custom user details
            details.getCustomUserDetails().setUsername(details.getCustomUser().getUsername());

            // Save custom user details
            CustomUserDetails customUserDetails = customUserDetailsRepository.save(details.getCustomUserDetails());

            // Save the custom user
            CustomUser savedUser = customUserRepository.save(details.getCustomUser());

            // Build the response object
            savedUsers.add(CustomUserAndDetails.builder()
                    .customUser(savedUser)
                    .customUserDetails(customUserDetails)
                    .build());
        }

        return savedUsers;
    }


    public CustomUser changeRole(CustomUser customUser) throws UserNotFoundException {
        if (customUserRepository.findById(customUser.getUsername()).isPresent()) {
            customUserRepository.save(customUser); // change update Logic
        }
        throw new UserAlreadyExistsException("User : " + customUser.getUsername() + " Not Found...!");
    }

    public String deleteUserById(String username) throws UserNotFoundException {
        if (customUserRepository.findById(username).isPresent()) {
            customUserRepository.deleteById(username); // change delete Logic
        }
        throw new UserAlreadyExistsException("User : " + username + " Not Found...!");
    }

    public List<CustomUserAndDetails> fetchUsers() {
        List<CustomUser> customUsers = customUserRepository.findAll();
        List<CustomUserDetails> details = customUserDetailsRepository.findAll();


        if (customUsers.isEmpty() || details.isEmpty()) {
            return new ArrayList<>();
        }

        // Create a map for fast lookup of CustomUser Details by username
        Map<String, CustomUserDetails> detailsMap = details.stream()
                .collect(Collectors.toMap(CustomUserDetails::getUsername, Function.identity()));

        // Build the list of CustomUser AndDetails
        return customUsers.stream()
                .map(customUser  -> {
                    CustomUserDetails userDetails = detailsMap.get(customUser .getUsername());
                    return CustomUserAndDetails.builder()
                            .customUser(customUser )
                            .customUserDetails(userDetails) // Will be null if not found
                            .build();
                })
                .collect(Collectors.toList());
    }
}


