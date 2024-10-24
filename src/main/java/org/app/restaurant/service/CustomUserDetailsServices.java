package org.app.restaurant.service;

import lombok.extern.slf4j.Slf4j;
import org.app.restaurant.dto.NewCustomUserRequest;
import org.app.restaurant.entity.CustomUser;
import org.app.restaurant.exception.UserAlreadyExistsException;
import org.app.restaurant.entity.CustomUserDetails;
import org.app.restaurant.exception.UserNotFoundException;
import org.app.restaurant.repository.CustomUserDetailsRepository;
import org.app.restaurant.repository.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    public NewCustomUserRequest saveUser(NewCustomUserRequest newCustomUserRequest) throws UserAlreadyExistsException { // Add Exception Handler
        if (customUserRepository.findById(newCustomUserRequest.getCustomUser().getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User : " + newCustomUserRequest.getCustomUser().getUsername() + " Already Existing...!");
        }

        newCustomUserRequest.getCustomUserDetails().setUsername(newCustomUserRequest.getCustomUser().getUsername());
        CustomUserDetails customUserDetails = customUserDetailsRepository.save(newCustomUserRequest.getCustomUserDetails());

        if(!customUserDetails.getUsername().isEmpty()) {
            return NewCustomUserRequest.builder()
                     .customUser(customUserRepository.save(newCustomUserRequest.getCustomUser()))
                     .customUserDetails(customUserDetails).build();
        }
        throw new RuntimeException("Unable To Save User : " + newCustomUserRequest.getCustomUser().getUsername());
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




}
