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
    public CouponAndDetailsRequest createCoupon(CouponAndDetailsRequest request) throws IllegalArgumentException, CouponAlreadyExistsException{

        if (request == null || request.getCoupon() == null || request.getCouponDetails() == null) {
            throw new IllegalArgumentException("Coupon or CouponDetails must not be null");
        }

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
    public List<CouponAndDetailsRequest> createCouponsFromList(List<CouponAndDetailsRequest> couponList) {
        return couponList.stream()
                .map(request -> {
                    // Detach the coupon object from the session if it's already managed
                    Coupon existingCoupon = couponRepository.findByCouponName(request.getCoupon().getCouponName());
                    if (existingCoupon != null) {
                        // Detach the existing coupon to prevent NonUniqueObjectException
//                        Session session = sessionFactory.getCurrentSession();
//                        session.evict(existingCoupon);
                        System.out.println(existingCoupon);
                    }

                    // Save the Coupon first
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

    public List<CouponAndDetailsRequest> getAllCouponsWithDetails() {
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

    public CouponAndDetailsRequest getCouponWithDetailsById(String couponId) {
        Optional<Coupon> coupon = couponRepository.findById(couponId);
        if (coupon.isPresent()) {
            // Fetch the CouponDetails for the given couponId directly
            List<CouponDetails> details = couponDetailsRepository.findByCouponId(couponId);
            return CouponAndDetailsRequest.builder()
                    .coupon(coupon.get())
                    .couponDetails(details.isEmpty() ? null : details.get(0))
                    .build();
        }
        return null; // You might consider throwing an exception or returning an Optional
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
