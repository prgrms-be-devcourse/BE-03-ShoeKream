package com.prgrms.kream.domain.coupon.model;

import static lombok.AccessLevel.PROTECTED;

import javax.persistence.Column;
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
@Table(name = "coupon")
@NoArgsConstructor(access = PROTECTED)
public class Coupon extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "discount_value", nullable = false, unique = false, length = 3)
	private int discountValue;

	@Column(name = "name", nullable = false, unique = false)
	private String name;

	@Column(name = "amount", nullable = false, unique = false)
	private int amount;

	@Builder
	public Coupon(Long id, int discountValue, String name, int amount) {
		this.id = id;
		this.discountValue = discountValue;
		this.name = name;
		this.amount = amount;
	}
}
