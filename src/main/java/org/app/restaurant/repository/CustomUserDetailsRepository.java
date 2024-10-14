package org.app.restaurant.repository;

import org.app.restaurant.model.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserDetailsRepository extends JpaRepository<CustomUserDetails, String> {

}
