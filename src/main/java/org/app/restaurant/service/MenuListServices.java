package org.app.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.restaurant.entity.MenuList;
import org.app.restaurant.exception.DuplicateMenuItemException;
import org.app.restaurant.repository.MenuListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MenuListServices {

    private final MenuListRepository menuListRepository;

    public MenuList addToMenuList(MenuList menuList) {

        MenuList existingMenuList = menuListRepository.findByItemAndPriceAndDescription(menuList.getItem(), menuList.getPrice(), menuList.getDescription());

        if (existingMenuList != null) {
            throw new DuplicateMenuItemException(menuList.getItem() + " is Already Existing ...!");
        }

        return menuListRepository.save(menuList);
    }

    public List<MenuList> addAllToMenuList(List<MenuList> menuList) {
        return menuListRepository.saveAll(menuList);
    }

    public List<MenuList> getMenuList() {
        return menuListRepository.findAll();
    }

    public Optional<MenuList> getMenuById(String id) {
        return menuListRepository.findById(id);
    }

    public Optional<MenuList> editMenuList(MenuList menuList) {
        Optional<MenuList> menuById = menuListRepository.findById(menuList.getId());
        if (menuById.isPresent()) {
            return Optional.of(menuListRepository.save(menuList));
        } else {
            return Optional.empty();
        }
    }

    public String deleteFromMenuList(String id) {
        Optional<MenuList> menuById = menuListRepository.findById(id);
        if (menuById.isPresent()) {
             //menuListRepository.deleteById(id);
             return String.format("Menu Id : %s Can not be Removed.", id);
        } else {
            return null;
        }
    }

}
