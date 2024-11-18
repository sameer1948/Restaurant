package org.app.restaurant.controller;

import org.app.restaurant.dto.TaxAndDetailsRequest;
import org.app.restaurant.entity.Tax;
import org.app.restaurant.service.TaxServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taxes")
public class TaxController {
    @Autowired
    private TaxServices taxServices;

    @PostMapping("/new-tax")
    public ResponseEntity<Void> createTaxAndDetails(@RequestBody TaxAndDetailsRequest request) {
        taxServices.createTaxAndDetails(request);
        return ResponseEntity.ok().build(); // Return 200 OK
    }

    @PostMapping("/new-taxes")
    public ResponseEntity<Void> bulkCreateTaxAndDetails(@RequestBody List<TaxAndDetailsRequest> requests) {
        taxServices.bulkCreateTaxAndDetails(requests);
        return ResponseEntity.ok().build(); // Return 200 OK
    }

    @GetMapping("/fetch-taxes")
    public ResponseEntity<List<TaxAndDetailsRequest>> getAllTaxes() {
        List<TaxAndDetailsRequest> taxes = taxServices.getAllTaxes();
        System.out.println(taxes);
        return ResponseEntity.ok(taxes);
    }

    @GetMapping("/fetch-tax/{id}")
    public ResponseEntity<Tax> getTaxById(@PathVariable String id) {
        Tax tax = taxServices.getTaxById(id);
        System.out.println(tax);
        return ResponseEntity.ok(tax);
    }

    @PutMapping("/update-tax/{id}")
    public ResponseEntity<Tax> updateTax(@PathVariable String id, @RequestBody Tax taxDetails) {
        Tax updatedTax = taxServices.updateTax(id, taxDetails);
        return ResponseEntity.ok(updatedTax);
    }

    @DeleteMapping("/remove-tax/{id}")
    public ResponseEntity<Void> deleteTax(@PathVariable String id) {  // Should not be used for delete
        taxServices.deleteTax(id);
        return ResponseEntity.noContent().build();
    }
}