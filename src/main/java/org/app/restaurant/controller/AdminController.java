package org.app.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.app.restaurant.dto.NewCustomUserRequest;
import org.app.restaurant.entity.CustomUser;
import org.app.restaurant.entity.MenuList;
import org.app.restaurant.exception.UserNotFoundException;
import org.app.restaurant.service.CustomUserDetailsServices;
import org.app.restaurant.service.MenuListServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing admin operations related to menu items and user management.
 */
@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final CustomUserDetailsServices customUserDetailsServices;
    private final MenuListServices menuListServices;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String testAdmin() {
        return "Admin Return";
    }

    /**
     * Adds a new menu item.
     *
     * @param menuList the menu item to be added.
     * @return ResponseEntity with the created menu item and status information.
     */
    @PostMapping("/add-menu")
    public ResponseEntity<MenuList> createNewMenuItem(@RequestBody MenuList menuList) {
        MenuList createdMenuItem = menuListServices.addToMenuList(menuList);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenuItem); // 201 Created
    }

    /**
     * Adds multiple menu items.
     *
     * @param menuList the list of menu items to be added.
     * @return ResponseEntity with the list of created menu items and status information.
     */
    @PostMapping("/add-all-menu")
    public ResponseEntity<List<MenuList>> createNewMenuList(@RequestBody List<MenuList> menuList) {
        List<MenuList> createdMenuItems = menuListServices.addAllToMenuList(menuList);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMenuItems); // 201 Created
    }

    /**
     * Fetches a single menu item by its ID.
     *
     * @param id the ID of the menu item.
     * @return ResponseEntity with the fetched menu item or a 404 if not found.
     */
    @GetMapping("/fetch-menu/{id}")
    public ResponseEntity<MenuList> fetchOneMenuItem(@PathVariable("id") Integer id) {
        Optional<MenuList> menuById = menuListServices.getMenuById(id);
        return menuById.map(ResponseEntity::ok) // 200 OK
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // 404 Not Found
    }

    /**
     * Fetches all menu items.
     *
     * @return ResponseEntity with the list of menu items.
     */
    @GetMapping("/fetch-all-menu")
    public ResponseEntity<List<MenuList>> fetchAllMenuItem() {
        List<MenuList> menuItems = menuListServices.getMenuList();
        return ResponseEntity.ok(menuItems); // 200 OK
    }

    /**
     * Updates an existing menu item.
     *
     * @param menuList the menu item with updated details.
     * @return ResponseEntity with the updated menu item.
     */
    @PatchMapping("/update-menu")
    public ResponseEntity<MenuList> updateMenuItem(@RequestBody MenuList menuList) {
        MenuList updatedMenuItem = menuListServices.editMenuList(menuList);
        return ResponseEntity.ok(updatedMenuItem); // 200 OK
    }

    /**
     * Deletes a menu item by its ID.
     *
     * @param id the ID of the menu item to be deleted.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/delete-menu/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable("id") Integer id) {
        String responseMessage = menuListServices.deleteFromMenuList(id);
        return ResponseEntity.ok(responseMessage); // 200 OK
    }

    /**
     * Adds a new user to the system.
     *
     * @param newCustomUserRequest the request object containing user details.
     * @return ResponseEntity with the created user details and status information.
     */
    @PostMapping("/new-user")
    public ResponseEntity<NewCustomUserRequest> addNewUser(@RequestBody NewCustomUserRequest newCustomUserRequest) {
        newCustomUserRequest.getCustomUser().setPassword(
                passwordEncoder.encode(newCustomUserRequest.getCustomUser().getPassword()));
        NewCustomUserRequest createdUser = customUserDetailsServices.saveUser(newCustomUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); // 201 Created
    }

    /**
     * Updates the role of a specified user.
     *
     * @param customUser the user object containing the user's details and the new role to be assigned.
     * @return ResponseEntity with the updated user request and status information.
     */
    @PatchMapping("/update-role")
    public ResponseEntity<CustomUser> modifyUserRole(@RequestBody CustomUser customUser) {
        try {
            CustomUser updatedUser = customUserDetailsServices.changeRole(customUser);
            return ResponseEntity.ok(updatedUser); // 200 OK
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
            String responseMessage = customUserDetailsServices.deleteUserById(id);
            return ResponseEntity.ok(responseMessage); // 200 OK
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); // 404 Not Found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"); // 500 Internal Server Error
        }
    }

}
