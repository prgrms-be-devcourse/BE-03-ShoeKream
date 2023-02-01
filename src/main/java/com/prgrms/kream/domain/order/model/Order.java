package com.prgrms.kream.domain.order.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import org.checkerframework.checker.units.qual.C;
import org.hibernate.validator.constraints.Length;

import com.prgrms.kream.common.model.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "`order`")
@NoArgsConstructor
public class Order extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "bid_id", nullable = false, unique = true)
	private Long bidId;

	@Column(name = "is_based_on_selling_bid", nullable = false, unique = false, columnDefinition = "BIT")
	private boolean isBasedOnSellingBid;

	@Column(name = "buyer_id", nullable = false, unique = false)
	private Long buyerId;

	@Column(name = "seller_id", nullable = false, unique = false)
	private Long sellerId;

	@Column(name = "product_option_id", nullable = false, unique = false)
	private Long productOptionId;

	@Column(name = "price", nullable = false, unique = false)
	private int price;

	@Column(name = "order_status", nullable = false, unique = false)
	@Enumerated(value = EnumType.STRING)
	private OrderStatus orderStatus;

	@Column(name = "order_request", nullable = false, unique = false)
	@Length(max = 50)
	private String orderRequest;

	@Builder
	Order(Long id, Long bidId, boolean isBasedOnSellingBid, Long buyerId, Long sellerId, Long productOptionId, int price,
			OrderStatus orderStatus,
			String orderRequest) {
		this.id = id;
		this.bidId = bidId;
		this.isBasedOnSellingBid = isBasedOnSellingBid;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.productOptionId = productOptionId;
		this.price = price;
		this.orderRequest = orderRequest;
		this.orderStatus = Objects.requireNonNullElse(orderStatus, OrderStatus.PAYED);
	}

	public boolean getIsBasedOnSellingBid(){
		return this.isBasedOnSellingBid;
	}
}
