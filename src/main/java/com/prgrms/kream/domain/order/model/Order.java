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
	Order(Long id, Long buyerId, Long sellerId, Long productOptionId, int price, OrderStatus orderStatus,
			String orderRequest) {
		this.id = id;
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.productOptionId = productOptionId;
		this.price = price;
		this.orderRequest = orderRequest;
		this.orderStatus = Objects.requireNonNullElse(orderStatus, OrderStatus.PAYED);
	}
}
