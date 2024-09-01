package org.app.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.app.restaurant.model.MenuList;
import org.app.restaurant.service.MenuListServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AdminController {

    private final MenuListServices menuListServices;

    @GetMapping("/")
    public String testAdmin() {
        return "Admin Return";
    }

    @PostMapping("/add-menu")
    private ResponseEntity<MenuList> createNewMenu(@RequestBody MenuList menuList) {
        return ResponseEntity.status(200).body(menuListServices.addToMenuList(menuList));
    }

    @PostMapping("/add-all-menu")
    private ResponseEntity<List<MenuList>> createNewMenu(@RequestBody List<MenuList> menuList) {
        return ResponseEntity.status(200).body(menuListServices.addAllToMenuList(menuList));
    }

    @PostMapping("/fetch-menu/{id}")
    private ResponseEntity<MenuList> fetchOne(@PathVariable("id") Integer id) {
        Optional<MenuList> menuById = menuListServices.getMenuById(id);
        return menuById.map(menuList -> ResponseEntity.status(200).body(menuList)).orElseGet(() -> ResponseEntity.status(400).body(new MenuList()));
    }

    @PostMapping("/fetch-all-menu")
    private ResponseEntity<List<MenuList>> fetchAll() {
        return ResponseEntity.status(200).body(menuListServices.getMenuList());
    }

    @PatchMapping("/update-menu")
    private ResponseEntity<MenuList> updateMenu(@RequestBody MenuList menuList) {
        return ResponseEntity.status(200).body(menuListServices.editMenuList(menuList));
    }

    @DeleteMapping("/delete-menu/{id}")
    private ResponseEntity<String> deleteMenu(@PathVariable("id") Integer id) {
        return ResponseEntity.status(200).body(menuListServices.deleteFromMenuList(id));
    }

}
