package org.app.restaurant.repository;

import org.app.restaurant.entity.MenuList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuListRepository extends JpaRepository<MenuList, Integer> {

}
