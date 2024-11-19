package org.app.restaurant.service;

import org.app.restaurant.dto.TaxAndDetailsRequest;
import org.app.restaurant.entity.Tax;
import org.app.restaurant.entity.TaxDetails;
import org.app.restaurant.repository.TaxDetailsRepository;
import org.app.restaurant.repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.app.restaurant.utils.DateUtility.getCurrentDate;

@Service
public class TaxServices {

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private TaxDetailsRepository taxDetailsRepository;

    @Transactional
    public TaxAndDetailsRequest newTax(TaxAndDetailsRequest request) {
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

        TaxDetails details = taxDetailsRepository.save(taxDetails);
        return TaxAndDetailsRequest.builder().tax(savedTax).taxDetails(details).build();
    }

    @Transactional
    public List<TaxAndDetailsRequest> newTaxes(List<TaxAndDetailsRequest> requests) {
        List<TaxAndDetailsRequest> savedTaxes = new ArrayList<>();
        if (requests == null || requests.isEmpty()) {
            throw new IllegalArgumentException("Request list cannot be null or empty.");
        }

        for (TaxAndDetailsRequest request : requests) {

            if (request == null || request.getTax() == null || request.getTaxDetails() == null) {
                throw new IllegalArgumentException("Tax or TaxDetails must not be null in each request.");
            }

           savedTaxes.add(this.newTax(request));
        }
        return savedTaxes;
    }

    public List<TaxAndDetailsRequest> fetchTaxes() {
        List<Tax> taxes = taxRepository.findAll();

        return taxes.stream()
                .map(tax -> {
                    Optional<TaxDetails> details = taxDetailsRepository.findById(tax.getTaxId());
                    return TaxAndDetailsRequest.builder()
                            .tax(tax)
                            .taxDetails(details.isEmpty() ? null : details.get())
                            .build();

                })
                .collect(Collectors.toList());
    }

    public Tax fetchTax(String id) {
        // Null check for ID
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Tax ID must not be null or empty.");
        }

        return taxRepository.findById(id).orElse(null);
    }

    public TaxAndDetailsRequest updateTax(TaxAndDetailsRequest request) {

        if (request == null || request.getTaxDetails() == null || request.getTax() == null) {
            throw new IllegalArgumentException("Tax or Tax details must not be null.");
        }
        // Null checks for ID and taxDetails
        if (request.getTax().getTaxId() == null || request.getTax().getTaxId().isEmpty()) {
            throw new IllegalArgumentException("Tax ID must not be null or empty.");
        }

        // Check if Tax exists before updating
        Optional<Tax> tax = taxRepository.findById(request.getTax().getTaxId());
        if (tax.isPresent()) {
            return TaxAndDetailsRequest.builder()
                    .tax(taxRepository.save(request.getTax()))
                    .taxDetails(taxDetailsRepository.save(request.getTaxDetails()))
                    .build();
        } else {
            throw new IllegalArgumentException("Tax with the given ID does not exist.");
        }
    }

    public void deleteTax(String id) {  // Can not be Deleted , It should be Disabled
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
