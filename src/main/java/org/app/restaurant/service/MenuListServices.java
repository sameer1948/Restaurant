package org.app.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.app.restaurant.model.MenuList;
import org.app.restaurant.repository.MenuListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuListServices {

    private final MenuListRepository menuListRepository;

    public MenuList addToMenuList(MenuList menuList) {
        return menuListRepository.save(menuList);
    }

    public List<MenuList> addAllToMenuList(List<MenuList> menuList) {
        return menuListRepository.saveAll(menuList);
    }

    public MenuList editMenuList(MenuList menuList) {
        Optional<MenuList> menuById = menuListRepository.findById(menuList.getId());
        if (menuById.isPresent()) {
            return menuListRepository.save(menuList);
        } else {
           return null;
        }
    }

    public String deleteFromMenuList(int id) {
        Optional<MenuList> menuById = menuListRepository.findById(id);
        if (menuById.isPresent()) {
             menuListRepository.deleteById(id);
             return String.format("Menu Id : %s is Successfully Removed.", id);
        } else {
            return null;
        }
    }

    public List<MenuList> getMenuList() {
        return menuListRepository.findAll();
    }

    public Optional<MenuList> getMenuById(int id) {
        return menuListRepository.findById(id);
    }
}
