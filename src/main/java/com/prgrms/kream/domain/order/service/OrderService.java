package com.prgrms.kream.domain.order.service;

import static com.prgrms.kream.common.mapper.OrderMapper.*;
import com.prgrms.kream.common.mapper.OrderMapper;
import com.prgrms.kream.domain.order.dto.request.OrderCancelRequest;
import com.prgrms.kream.domain.order.dto.request.OrderCreateServiceRequest;
import com.prgrms.kream.domain.order.dto.request.OrderFindRequest;
import com.prgrms.kream.domain.order.dto.request.OrderUpdateStatusRequest;
import com.prgrms.kream.domain.order.dto.response.OrderCreateResponse;
import com.prgrms.kream.domain.order.dto.response.OrderFindResponse;
import com.prgrms.kream.domain.order.dto.response.OrderUpdateStatusResponse;
import com.prgrms.kream.domain.order.repository.OrderRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;

	@Transactional
	public OrderCreateResponse registerOrder(OrderCreateServiceRequest orderCreateServiceRequest) {
		return toOrderCreateResponse(orderRepository.save(toOrder(orderCreateServiceRequest)));
	}

	@Transactional
	public void deleteOrder(OrderCancelRequest orderCancelRequest) {
		orderRepository.deleteById(orderCancelRequest.id());
	}

	@Transactional(readOnly = true)
	public OrderFindResponse findById(OrderFindRequest orderFindRequest) {
		return orderRepository
				.findById(orderFindRequest.id())
				.map(OrderMapper::toOrderFindResponse)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Transactional
	public OrderUpdateStatusResponse updateOrderStatus(OrderUpdateStatusRequest orderUpdateStatusRequest) {
		orderRepository.updateOrderStatusById(orderUpdateStatusRequest.orderStatus(), orderUpdateStatusRequest.id());
		return new OrderUpdateStatusResponse(
				orderRepository.findById(orderUpdateStatusRequest.id())
						.orElseThrow(EntityNotFoundException::new)
						.getOrderStatus()
		);
	}
}
