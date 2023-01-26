package com.prgrms.kream.domain.coupon.model;

import static lombok.AccessLevel.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prgrms.kream.common.model.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "coupon_event")
@NoArgsConstructor(access = PROTECTED)
public class CouponEvent extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long couponId;
	private Long memberId;

	@Builder
	public CouponEvent(Long id, Long couponId, Long memberId) {
		this.id = id;
		this.couponId = couponId;
		this.memberId = memberId;
	}
}
