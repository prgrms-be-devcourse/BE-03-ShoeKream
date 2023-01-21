package com.prgrms.kream.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.coupon.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
