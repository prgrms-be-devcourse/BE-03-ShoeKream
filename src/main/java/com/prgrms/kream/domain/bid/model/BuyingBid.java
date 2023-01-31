package com.prgrms.kream.domain.bid.model;

import static lombok.AccessLevel.*;
import com.prgrms.kream.common.model.BaseTimeEntity;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "buying_bid")
@NoArgsConstructor(access = PROTECTED)
public class BuyingBid extends BaseTimeEntity {
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

	@Column(name = "is_deleted", nullable = false, unique = false, columnDefinition = "BIT")
	private boolean isDeleted;

	@Version
	private Long version;

	@Builder
	public BuyingBid(Long id, Long memberId, Long productOptionId, int price, LocalDateTime validUntil,
			boolean isDeleted) {
		this.id = id;
		this.memberId = memberId;
		this.productOptionId = productOptionId;
		this.price = price;
		this.validUntil = Objects.requireNonNullElse(validUntil, LocalDateTime.now().plusDays(30));
		this.isDeleted = Objects.requireNonNullElse(isDeleted, false);
	}

	public void delete() {
		this.isDeleted = true;
	}

	public void restore() {
		this.isDeleted = false;
	}
}
