package org.app.restaurant.controller;

import jakarta.validation.Valid;
import org.app.restaurant.dto.CouponAndDetailsRequest;
import org.app.restaurant.exception.CouponAlreadyExistsException;
import org.app.restaurant.service.CouponServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponServices couponServices;

    @PostMapping(value = "/add-coupon")
    public ResponseEntity<?> createCoupon(@Valid @RequestBody CouponAndDetailsRequest request) {
        try {
            // Basic validation: Check if the request contains the necessary coupon and couponDetails objects.
            if (request == null || request.getCoupon() == null || request.getCouponDetails() == null) {
                return new ResponseEntity<>("Coupon or CouponDetails must not be null", HttpStatus.BAD_REQUEST);
            }

            CouponAndDetailsRequest response = couponServices.createCoupon(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created

        } catch (IllegalArgumentException | CouponAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request with message
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }


    @PostMapping("/add-coupons")
    public ResponseEntity<List<CouponAndDetailsRequest>> addCoupons(@RequestBody List<CouponAndDetailsRequest> coupons) {
        List<CouponAndDetailsRequest> savedCoupons = couponServices.createCouponsFromList(coupons);
        return new ResponseEntity<>(savedCoupons, HttpStatus.CREATED); // 201 Created
    }

    @GetMapping("/fetch-coupons")
    public ResponseEntity<List<CouponAndDetailsRequest>> getAllCouponsWithDetails() {
        List<CouponAndDetailsRequest> couponsWithDetails = couponServices.getAllCouponsWithDetails();
        if (couponsWithDetails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if no coupons found
        }
        return new ResponseEntity<>(couponsWithDetails, HttpStatus.OK); // 200 OK
    }

    @GetMapping("/fetch-coupon/{couponId}")
    public ResponseEntity<CouponAndDetailsRequest> getCouponWithDetailsById(@PathVariable String couponId) {
        CouponAndDetailsRequest couponWithDetails = couponServices.getCouponWithDetailsById(couponId);
        if (couponWithDetails == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found if coupon is not found
        }
        return new ResponseEntity<>(couponWithDetails, HttpStatus.OK); // 200 OK
    }

    @DeleteMapping("/remove-coupon/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable String couponId) { // Coupon Can not be Deleted , It should be Disabled
        boolean isDeleted = couponServices.deleteCoupon(couponId);
        if (isDeleted) {
            return new ResponseEntity<>("Coupon deleted successfully", HttpStatus.NO_CONTENT); // 204 No Content
        }
        return new ResponseEntity<>("Coupon not found", HttpStatus.NOT_FOUND); // 404 Not Found if coupon does not exist
    }
}
