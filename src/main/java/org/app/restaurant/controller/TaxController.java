package org.app.restaurant.controller;

import org.app.restaurant.dto.TaxAndDetailsRequest;
import org.app.restaurant.entity.Tax;
import org.app.restaurant.service.TaxServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.app.restaurant.constatnts.Constants.UN_EXP_ERROR;

@RestController
@RequestMapping("/taxes")
public class TaxController {


    @Autowired
    private TaxServices taxServices;

    @PostMapping("/new-tax")
    public ResponseEntity<?> newTax(@RequestBody TaxAndDetailsRequest request) {
        try {
            TaxAndDetailsRequest response = taxServices.newTax(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request with message
        } catch (Exception e) {
            return new ResponseEntity<>( UN_EXP_ERROR + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @PostMapping("/new-taxes")
    public ResponseEntity<List<TaxAndDetailsRequest>> newTaxes(@RequestBody List<TaxAndDetailsRequest> requests) {
        List<TaxAndDetailsRequest> saved = taxServices.newTaxes(requests);
        return new ResponseEntity<>(saved, HttpStatus.CREATED); // 201 Created
    }

    @GetMapping("/fetch-taxes")
    public ResponseEntity<List<TaxAndDetailsRequest>> fetchTaxes() {
        List<TaxAndDetailsRequest> taxes = taxServices.fetchTaxes();
        return ResponseEntity.ok(taxes);
    }

    @GetMapping("/fetch-tax/{id}")
    public ResponseEntity<?> fetchTax(@PathVariable String id) {
        try {
            Tax tax = taxServices.fetchTax(id);
            return new ResponseEntity<>(tax, HttpStatus.FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request with message
        } catch (Exception e) {
            return new ResponseEntity<>(UN_EXP_ERROR + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @PutMapping("/update-tax/{id}")
    public ResponseEntity<?> updateTax(@PathVariable String id, @RequestBody Tax taxDetails) {
        try {
            Tax updatedTax = taxServices.updateTax(id, taxDetails);
            return new ResponseEntity<>(updatedTax, HttpStatus.ACCEPTED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request with message
        } catch (Exception e) {
            return new ResponseEntity<>(UN_EXP_ERROR + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }

    @DeleteMapping("/remove-tax/{id}")
    public ResponseEntity<?> deleteTax(@PathVariable String id) {  // Should not be used for delete
        try {
            taxServices.deleteTax(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request with message
        } catch (Exception e) {
            return new ResponseEntity<>(UN_EXP_ERROR + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}