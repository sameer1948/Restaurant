package org.app.restaurant.controller;

import lombok.extern.slf4j.Slf4j;
import org.app.restaurant.dto.CustomUserAndDetails;
import org.app.restaurant.exception.UserNotFoundException;
import org.app.restaurant.service.CustomUserDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private CustomUserDetailsServices customUserDetailsServices;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Adds a new user to the system.
     *
     * @param customUserAndDetails the request object containing user details.
     * @return ResponseEntity with the created user details and status information.
     */
    @PostMapping("/new-user") // Public End-Point; But after Integration It should be Private End point
    public ResponseEntity<CustomUserAndDetails> saveUser(@RequestBody CustomUserAndDetails customUserAndDetails) {
        customUserAndDetails.getCustomUser().setPassword(
                passwordEncoder.encode(customUserAndDetails.getCustomUser().getPassword()));
        CustomUserAndDetails createdUser = customUserDetailsServices.saveUser(customUserAndDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); // 201 Created
    }


    @PostMapping("/new-users") // Public End-Point; But after Integration It should be Private End point
    public List<CustomUserAndDetails> saveUsers(@RequestBody List<CustomUserAndDetails> customUserAndDetails) {
        // Encode passwords for all new users
        customUserAndDetails.forEach(details -> {
            String encodedPassword = passwordEncoder.encode(details.getCustomUser().getPassword());
            details.getCustomUser().setPassword(encodedPassword);
        });

        // Save the users
        return customUserDetailsServices.saveUsers(customUserAndDetails);
    }

    /**
     * Fetches  user By Id.
     *
     * @return ResponseEntity with the User.
     */
    @PostMapping("/fetch-user/{userName}")
    public ResponseEntity<CustomUserAndDetails> fetchUserById(@PathVariable("userName") String userName) {
        Optional<CustomUserAndDetails> customUserAndDetails = customUserDetailsServices.fetchUser(userName);
        return customUserAndDetails.map(ResponseEntity::ok) // 200 OK
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // 404 Not Found
    }

    /**
     * Fetches all users.
     *
     * @return ResponseEntity with the list of User items.
     */
    @GetMapping("/fetch-users")
    public ResponseEntity<List<CustomUserAndDetails>> fetchUsers() {
        return ResponseEntity.ok(customUserDetailsServices.fetchUsers()); // 200 OK
    }


    /**
     * Updates the role of a specified user.
     *
     * @param customUserAndDetails the user object containing the user's details and the new role to be assigned.
     * @return ResponseEntity with the updated user request and status information.
     */
    @PatchMapping("/update-user")
    public ResponseEntity<CustomUserAndDetails> updateUser(@RequestBody CustomUserAndDetails customUserAndDetails) {
        try {
            CustomUserAndDetails updatedUser = customUserDetailsServices.updateUser(customUserAndDetails);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedUser); // 200 OK
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to be deleted.
     * @return ResponseEntity with a success message or an error message if not found.
     */
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {
        try {
            String responseMessage = customUserDetailsServices.deleteUser(id);
            return ResponseEntity.ok(responseMessage); // 200 OK
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"); // 500 Internal Server Error
        }
    }


}
