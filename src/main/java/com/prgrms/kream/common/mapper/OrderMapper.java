package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.order.dto.request.OrderCreateServiceRequest;
import com.prgrms.kream.domain.order.dto.response.OrderCreateResponse;
import com.prgrms.kream.domain.order.model.Order;

public class OrderMapper {
	public static Order toOrder(OrderCreateServiceRequest orderCreateServiceRequest) {
		return Order.builder()
				.id(orderCreateServiceRequest.id())
				.buyerId(orderCreateServiceRequest.buyerId())
				.sellerId(orderCreateServiceRequest.sellerId())
				.productOptionId(orderCreateServiceRequest.productOptionId())
				.price(orderCreateServiceRequest.price())
				.orderRequest(orderCreateServiceRequest.orderRequest())
				.build();
	}

	public static OrderCreateResponse toOrderCreateResponse(Order order) {
		return new OrderCreateResponse(order.getId());
	}
}
