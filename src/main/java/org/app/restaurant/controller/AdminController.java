package org.app.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.app.restaurant.entity.MenuList;
import org.app.restaurant.service.MenuListServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final MenuListServices menuListServices;

    @GetMapping("/")
    public String testAdmin() {
        return "Admin Return";
    }

    @PostMapping("/add-menu")
    private ResponseEntity<MenuList> createNewMenuItem(@RequestBody MenuList menuList) {
        return ResponseEntity.status(200).body(menuListServices.addToMenuList(menuList));
    }

    @PostMapping("/add-all-menu")
    private ResponseEntity<List<MenuList>> createNewMenuList(@RequestBody List<MenuList> menuList) {
        return ResponseEntity.status(200).body(menuListServices.addAllToMenuList(menuList));
    }

    @PostMapping("/fetch-menu/{id}")
    private ResponseEntity<MenuList> fetchOneMenuItem(@PathVariable("id") Integer id) {
        Optional<MenuList> menuById = menuListServices.getMenuById(id);
        return menuById.map(menuList -> ResponseEntity.status(200).body(menuList))
                .orElseGet(() -> ResponseEntity.status(400).body(new MenuList()));
    }

    @PostMapping("/fetch-all-menu") //Public End Point
    private ResponseEntity<List<MenuList>> fetchAllMenuItem() {
        return ResponseEntity.status(200).body(menuListServices.getMenuList());
    }

    @PatchMapping("/update-menu")
    private ResponseEntity<MenuList> updateMenuItem(@RequestBody MenuList menuList) {
        return ResponseEntity.status(200).body(menuListServices.editMenuList(menuList));
    }

    @DeleteMapping("/delete-menu/{id}")
    private ResponseEntity<String> deleteMenuItem(@PathVariable("id") Integer id) {
        return ResponseEntity.status(200).body(menuListServices.deleteFromMenuList(id));
    }

}
