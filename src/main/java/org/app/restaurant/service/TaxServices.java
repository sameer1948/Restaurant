package org.app.restaurant.service;

import org.app.restaurant.dto.TaxAndDetailsRequest;
import org.app.restaurant.entity.Tax;
import org.app.restaurant.entity.TaxDetails;
import org.app.restaurant.repository.TaxDetailsRepository;
import org.app.restaurant.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.app.restaurant.utils.DateUtility.getCurrentDate;

@Service
public class TaxServices {

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private TaxDetailsRepository taxDetailsRepository;

    @Transactional
    public void createTaxAndDetails(TaxAndDetailsRequest request) {
        if (request == null || request.getTax() == null || request.getTaxDetails() == null) {
            throw new IllegalArgumentException("Tax or TaxDetails must not be null.");
        }

        if (isDuplicateTax(request.getTax().getTaxType(), request.getTax().getValue())) {
            throw new IllegalArgumentException("TaxDetails with the given taxId already exists.");
        }

        Tax savedTax = taxRepository.save(request.getTax());

        TaxDetails taxDetails = request.getTaxDetails();
        taxDetails.setTaxId(savedTax.getTaxId());
        taxDetails.setTimeStamp(getCurrentDate());

        taxDetailsRepository.save(taxDetails);
    }

    @Transactional
    public void bulkCreateTaxAndDetails(List<TaxAndDetailsRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("Request list cannot be null or empty.");
        }

        for (TaxAndDetailsRequest request : requests) {

            if (request == null || request.getTax() == null || request.getTaxDetails() == null) {
                throw new IllegalArgumentException("Tax or TaxDetails must not be null in each request.");
            }

            this.createTaxAndDetails(request);
        }
    }

    public List<Tax> getAllTaxes() {
        return taxRepository.findAll();
    }

    public Tax getTaxById(String id) {
        // Null check for ID
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Tax ID must not be null or empty.");
        }

        return taxRepository.findById(id).orElse(null);
    }

    public Tax updateTax(String id, Tax taxDetails) {
        // Null checks for ID and taxDetails
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Tax ID must not be null or empty.");
        }
        if (taxDetails == null) {
            throw new IllegalArgumentException("Tax details must not be null.");
        }

        // Check if Tax exists before updating
        Tax tax = taxRepository.findById(id).orElse(null);
        if (tax != null) {
            tax.setTaxType(taxDetails.getTaxType());
            tax.setValue(taxDetails.getValue());
            tax.setStatus(taxDetails.isStatus());
            return taxRepository.save(tax);
        } else {
            throw new IllegalArgumentException("Tax with the given ID does not exist.");
        }
    }

    public void deleteTax(String id) {
        // Null check for ID
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Tax ID must not be null or empty.");
        }

        // Check if Tax exists before deleting
        if (!taxRepository.existsById(id)) {
            throw new IllegalArgumentException("Tax with the given ID does not exist.");
        }

        taxRepository.deleteById(id);
    }

    private boolean isDuplicateTax(String taxType, double value) {
        return taxRepository.findByTaxTypeAndValue(taxType, value).isPresent();
    }

}
