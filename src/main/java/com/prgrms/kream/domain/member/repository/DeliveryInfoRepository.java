package com.prgrms.kream.domain.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.member.model.DeliveryInfo;

public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {
	Page<DeliveryInfo> findAllByMemberId(Long memberId, Pageable pageable);
}
