package org.app.restaurant.service;

import org.app.restaurant.dto.CouponAndDetailsRequest;
import org.app.restaurant.entity.Coupon;
import org.app.restaurant.entity.CouponDetails;
import org.app.restaurant.exception.CouponAlreadyExistsException;
import org.app.restaurant.repository.CouponDetailsRepository;
import org.app.restaurant.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CouponServices {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponDetailsRepository couponDetailsRepository;

    @Transactional
    public CouponAndDetailsRequest createCoupon(CouponAndDetailsRequest request) throws IllegalArgumentException, CouponAlreadyExistsException {

        if (request == null || request.getCoupon() == null || request.getCouponDetails() == null) {
            throw new IllegalArgumentException("Coupon or CouponDetails must not be null");
        }

        // Check if the coupon already exists by name
        boolean couponExists = couponRepository.existsByCouponName(request.getCoupon().getCouponName());
        if (couponExists) {
            throw new CouponAlreadyExistsException("Coupon with the same name already exists!");
        }

        // Save the Coupon and CouponDetails
        Coupon savedCoupon = couponRepository.save(request.getCoupon());

        // Set the couponId on couponDetails before saving
        request.getCouponDetails().setCouponId(savedCoupon.getCouponId());
        CouponDetails savedCouponDetails = couponDetailsRepository.save(request.getCouponDetails());

        return CouponAndDetailsRequest.builder()
                .coupon(savedCoupon)
                .couponDetails(savedCouponDetails)
                .build();
    }

    @Transactional
    public List<CouponAndDetailsRequest> createCouponsFromList(List<CouponAndDetailsRequest> couponList) throws IllegalArgumentException, CouponAlreadyExistsException {
        return couponList.stream()
                .map(request -> {
                    // Avoid entity duplication by checking if the coupon exists
                    Coupon existingCoupon = couponRepository.findByCouponName(request.getCoupon().getCouponName());
                    if (existingCoupon != null) {
                        // If the coupon already exists, we will skip saving it and directly use the existing coupon
                        // We still need to set couponId for CouponDetails
                        request.getCouponDetails().setCouponId(existingCoupon.getCouponId());
                        CouponDetails savedCouponDetails = couponDetailsRepository.save(request.getCouponDetails());

                        return CouponAndDetailsRequest.builder()
                                .coupon(existingCoupon)  // Use existing coupon
                                .couponDetails(savedCouponDetails)
                                .build();
                    }

                    // Save the Coupon if it doesn't exist
                    Coupon savedCoupon = couponRepository.save(request.getCoupon());

                    // Set the couponId on couponDetails before saving
                    request.getCouponDetails().setCouponId(savedCoupon.getCouponId());
                    CouponDetails savedCouponDetails = couponDetailsRepository.save(request.getCouponDetails());

                    return CouponAndDetailsRequest.builder()
                            .coupon(savedCoupon)
                            .couponDetails(savedCouponDetails)
                            .build();
                })
                .collect(Collectors.toList());
    }

    public List<CouponAndDetailsRequest> fetchCoupons() {
        List<Coupon> coupons = couponRepository.findAll();

        // Fetch CouponDetails for each coupon in a more efficient manner
        return coupons.stream()
                .map(coupon -> {
                    List<CouponDetails> details = couponDetailsRepository.findByCouponId(coupon.getCouponId());
                    return CouponAndDetailsRequest.builder()
                            .coupon(coupon)
                            .couponDetails(details.isEmpty() ? null : details.get(0))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public Optional<CouponAndDetailsRequest> fetchCoupon(String couponId) {
        Optional<Coupon> coupon = couponRepository.findById(couponId);
        if (coupon.isPresent()) {
            // Fetch the CouponDetails for the given couponId directly
            List<CouponDetails> details = couponDetailsRepository.findByCouponId(couponId);
            return Optional.of(CouponAndDetailsRequest.builder()
                    .coupon(coupon.get())
                    .couponDetails(details.isEmpty() ? null : details.get(0))
                    .build());
        }
        return Optional.empty();
    }

    public Optional<CouponAndDetailsRequest> updateCoupon(CouponAndDetailsRequest request) {
        Optional<Coupon> coupon = couponRepository.findById(request.getCoupon().getCouponId());
        if (coupon.isPresent()) {
            // Fetch the CouponDetails for the given couponId directly
            Coupon savedCoupon = couponRepository.save(request.getCoupon());
            CouponDetails savedDetails = couponDetailsRepository.save(request.getCouponDetails());

            return Optional.of(CouponAndDetailsRequest.builder()
                    .coupon(savedCoupon)
                    .couponDetails(savedDetails)
                    .build());
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteCoupon(String couponId) {
        if (couponRepository.existsById(couponId)) {
            couponRepository.deleteById(couponId);
            return true;
        }
        return false;
    }

    // Other methods for creating and updating CouponDetails as required...
}
