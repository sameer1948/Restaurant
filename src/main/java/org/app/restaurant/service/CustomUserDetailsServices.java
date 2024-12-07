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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.app.restaurant.constatnts.Constants.*;

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
                .orElseThrow(() -> new UsernameNotFoundException(USER_NAME_NOT_FOUND.replace(OPT_STRING, username)));
    }

    public CustomUserAndDetails saveUser(CustomUserAndDetails customUserAndDetails) throws UserAlreadyExistsException { // Add Exception Handler
        if (customUserRepository.findById(customUserAndDetails.getCustomUser().getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(USER_NAME_EXISTED.replace(OPT_STRING, customUserAndDetails.getCustomUser().getUsername()));
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

    public List<CustomUserAndDetails> saveUsers(List<CustomUserAndDetails> customUserAndDetails) throws UserAlreadyExistsException {
        List<CustomUserAndDetails> savedUsers = new ArrayList<>();

        for (CustomUserAndDetails details : customUserAndDetails) {
            // Check if the user already exists
            if (customUserRepository.findById(details.getCustomUser().getUsername()).isPresent()) {
                throw new UserAlreadyExistsException(USER_NAME_EXISTED.replace(OPT_STRING, details.getCustomUser().getUsername()));
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

    public Optional<CustomUserAndDetails> fetchUser(String username) {
        Optional<CustomUser> savedUser = customUserRepository.findByUsername((username));
        if (savedUser.isPresent()) {
            CustomUserDetails savedDetails = customUserDetailsRepository.findByUsername(username);
            savedUser.get().getPassword();
            return Optional.of(CustomUserAndDetails.builder()
                    .customUser(savedUser.get())
                    .customUserDetails(savedDetails)
                    .build());
        } else {
            return Optional.empty();
        }

    }

    public List<CustomUserAndDetails> fetchUsers() {
        List<CustomUser> customUsers = customUserRepository.findAll();
        customUsers.stream().forEach(System.out::println);
        if (customUsers.isEmpty()) {
            return null;
        }

        return customUsers.stream().map(customUser -> {
            CustomUserDetails details = customUserDetailsRepository.findByUsername(customUser.getUsername());
            return CustomUserAndDetails.builder().customUser(customUser).customUserDetails(details).build();

        }).collect(Collectors.toList());

    }


    public CustomUserAndDetails updateUser(CustomUserAndDetails userAndDetails) throws UserNotFoundException {
        if (customUserRepository.findById(userAndDetails.getCustomUser().getUsername()).isPresent()) {
            CustomUser savedUser = customUserRepository.save(userAndDetails.getCustomUser()); // change update Logic
            CustomUserDetails savedDetails = customUserDetailsRepository.save(userAndDetails.getCustomUserDetails());
            return CustomUserAndDetails.builder().customUser(savedUser).customUserDetails(savedDetails).build();
        }
        throw new UserNotFoundException(USER_NAME_NOT_FOUND.replace(OPT_STRING, userAndDetails.getCustomUser().getUsername()));
    }

    public CustomUserAndDetails updatePassword(String username, String password) throws UserNotFoundException {
        Optional<CustomUser> optUser = customUserRepository.findById(username);
        if (optUser.isPresent()) {
            CustomUser user = optUser.get();
            user.setPassword(password);
            CustomUser savedUser = customUserRepository.save(user); // change update Logic
            CustomUserDetails savedDetails = customUserDetailsRepository.findByUsername(username);
            return CustomUserAndDetails.builder().customUser(savedUser).customUserDetails(savedDetails).build();
        }
        throw new UserNotFoundException(USER_NAME_NOT_FOUND.replace(OPT_STRING, username));
    }


    @Transactional
    public String deleteUser(String username) throws UserNotFoundException {
        Optional<CustomUser> userOptional = customUserRepository.findById(username);
        if (userOptional.isPresent()) {
            customUserDetailsRepository.deleteByUsername(username);
            customUserRepository.delete(userOptional.get());
            return "User Removed Successfully...!";
        }
        throw new UserNotFoundException(USER_NAME_NOT_FOUND.replace(OPT_STRING, username));
    }

}


