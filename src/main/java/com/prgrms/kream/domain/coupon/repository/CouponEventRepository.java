package com.prgrms.kream.domain.coupon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.coupon.model.CouponEvent;
import com.prgrms.kream.domain.member.model.Member;

public interface CouponEventRepository extends JpaRepository<CouponEvent, Long> {
	Optional<CouponEvent> findByMember(Member member);
}
