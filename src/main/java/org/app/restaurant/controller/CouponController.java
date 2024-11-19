package org.app.restaurant.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.app.restaurant.dto.CouponAndDetailsRequest;
import org.app.restaurant.exception.CouponAlreadyExistsException;
import org.app.restaurant.service.CouponServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.app.restaurant.constatnts.Constants.UN_EXP_ERROR;

@RestController
@RequestMapping("/coupons")
@Slf4j
public class CouponController {

    @Autowired
    private CouponServices couponServices;

    @PostMapping(value = "/add-coupon")
    public ResponseEntity<?> createCoupon(@Valid @RequestBody CouponAndDetailsRequest request) {
        try {
            CouponAndDetailsRequest response = couponServices.createCoupon(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created

        } catch (IllegalArgumentException | CouponAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request with message
        } catch (Exception e) {
            return new ResponseEntity<>(UN_EXP_ERROR + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }


    @PostMapping("/add-coupons")
    public ResponseEntity<List<CouponAndDetailsRequest>> addCoupons(@RequestBody List<CouponAndDetailsRequest> coupons) {
        List<CouponAndDetailsRequest> savedCoupons = couponServices.createCouponsFromList(coupons);
        return new ResponseEntity<>(savedCoupons, HttpStatus.CREATED); // 201 Created
    }

    @GetMapping("/fetch-coupon/{couponId}")
    public ResponseEntity<CouponAndDetailsRequest> fetchCoupon(@PathVariable String couponId) {
        Optional<CouponAndDetailsRequest> couponWithDetails = couponServices.fetchCoupon(couponId);
        return couponWithDetails
                .map(request -> new ResponseEntity<>(request, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fetch-coupons")
    public ResponseEntity<List<CouponAndDetailsRequest>> fetchCoupons() {
        List<CouponAndDetailsRequest> couponsWithDetails = couponServices.fetchCoupons();
        if (couponsWithDetails.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if no coupons found
        }
        return new ResponseEntity<>(couponsWithDetails, HttpStatus.OK); // 200 OK
    }

    @PatchMapping("/update-coupon")
    public ResponseEntity<CouponAndDetailsRequest> updateCoupon(@RequestBody CouponAndDetailsRequest request) {
        Optional<CouponAndDetailsRequest> couponDetails = couponServices.updateCoupon(request);
        return couponDetails
                .map(couponDetail -> new ResponseEntity<>(couponDetail, HttpStatus.ACCEPTED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
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
