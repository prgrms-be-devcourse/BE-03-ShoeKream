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
	private final OrderRepository repository;

	@Transactional
	public OrderCreateResponse register(OrderCreateServiceRequest orderCreateServiceRequest) {
		return toOrderCreateResponse(repository.save(toOrder(orderCreateServiceRequest)));
	}

	@Transactional
	public void deleteById(OrderCancelRequest orderCancelRequest) {
		repository.deleteById(orderCancelRequest.id());
	}

	@Transactional(readOnly = true)
	public OrderFindResponse findById(OrderFindRequest orderFindRequest) {
		return repository
				.findById(orderFindRequest.id())
				.map(OrderMapper::toOrderFindResponse)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Transactional
	public OrderUpdateStatusResponse updateStatus(OrderUpdateStatusRequest orderUpdateStatusRequest) {
		repository.updateOrderStatusById(orderUpdateStatusRequest.orderStatus(), orderUpdateStatusRequest.id());
		return new OrderUpdateStatusResponse(
				repository.findById(orderUpdateStatusRequest.id())
						.orElseThrow(EntityNotFoundException::new)
						.getOrderStatus()
		);
	}
}
