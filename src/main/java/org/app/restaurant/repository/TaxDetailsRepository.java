package org.app.restaurant.repository;

import org.app.restaurant.entity.TaxDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxDetailsRepository extends JpaRepository<TaxDetails, String> {
    // Custom query methods can be added here
}
