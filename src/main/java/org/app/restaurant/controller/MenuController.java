package org.app.restaurant.controller;


import lombok.RequiredArgsConstructor;
import org.app.restaurant.entity.MenuList;
import org.app.restaurant.service.MenuListServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/menu")
@RequiredArgsConstructor
@RestController
public class MenuController {

    private final MenuListServices menuListServices;

    @GetMapping("/")
    public String testMenu() {
        return "Menu Return";
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
    @PostMapping("/add-menus")
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
    public ResponseEntity<MenuList> fetchOneMenuItem(@PathVariable("id") String id) {
        Optional<MenuList> menuById = menuListServices.getMenuById(id);
        return menuById.map(ResponseEntity::ok) // 200 OK
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // 404 Not Found
    }

    /**
     * Fetches all menu items.
     *
     * @return ResponseEntity with the list of menu items.
     */
    @GetMapping("/fetch-menus")
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
        Optional<MenuList> menuById = menuListServices.editMenuList(menuList);
        return menuById.map(ResponseEntity::ok) // 200 OK
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // 404 Not Found
    }

    /**
     * Deletes a menu item by its ID.
     *
     * @param id the ID of the menu item to be deleted.
     * @return ResponseEntity with a success message.
     */
    @DeleteMapping("/delete-menu/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable("id") String id) {
        String responseMessage = menuListServices.deleteFromMenuList(id);
        return ResponseEntity.ok(responseMessage); // 200 OK
    }

}
