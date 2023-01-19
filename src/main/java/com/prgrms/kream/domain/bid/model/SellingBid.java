package com.prgrms.kream.domain.bid.model;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;

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
@Table(name = "selling_bid")
@NoArgsConstructor(access = PROTECTED)
public class SellingBid extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "member_id", nullable = false, unique = false)
	private Long memberId;

	@Column(name = "product_option_id", nullable = false, unique = false)
	private Long productOptionId;

	@Column(name = "price", nullable = false, unique = false)
	private int price;

	@Column(name = "valid_until", nullable = false, unique = false)
	private LocalDateTime validUntil;

	@Builder
	public SellingBid(Long id, Long memberId, Long productOptionId, int price, LocalDateTime validUntil) {
		this.id = id;
		this.memberId = memberId;
		this.productOptionId = productOptionId;
		this.price = price;
		this.validUntil = validUntil;
	}
}