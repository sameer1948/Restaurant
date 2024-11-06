package org.app.restaurant.repository;

import org.app.restaurant.entity.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {
    Optional<Tax> findByTaxTypeAndValue(String taxType, double value);

}
