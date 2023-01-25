package com.prgrms.kream.domain.coupon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.coupon.model.CouponEvent;

public interface CouponEventRepository extends JpaRepository<CouponEvent, Long> {
	Optional<CouponEvent> findByMemberId(Long memberId);
}
