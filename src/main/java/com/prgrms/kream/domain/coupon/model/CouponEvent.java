package com.prgrms.kream.domain.coupon.model;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.prgrms.kream.common.model.BaseTimeEntity;
import com.prgrms.kream.domain.member.model.Member;

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

	@ManyToOne(fetch = FetchType.LAZY)
	private Coupon coupon;

	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;

	@Builder
	public CouponEvent(Coupon coupon, Member member) {
		this.coupon = coupon;
		this.member = member;
	}
}
