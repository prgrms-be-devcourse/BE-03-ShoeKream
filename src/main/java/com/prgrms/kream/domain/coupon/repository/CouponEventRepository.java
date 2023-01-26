package com.prgrms.kream.domain.coupon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.coupon.model.CouponEvent;

public interface CouponEventRepository extends JpaRepository<CouponEvent, Long> {
	boolean existsByMemberIdAndCouponId(Long memberId, Long couponId);
}
