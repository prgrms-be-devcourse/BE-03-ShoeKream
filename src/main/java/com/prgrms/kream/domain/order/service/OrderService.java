package com.prgrms.kream.domain.order.service;

import com.prgrms.kream.domain.order.dto.request.OrderCreateServiceRequest;
import com.prgrms.kream.domain.order.dto.response.OrderCreateResponse;
import com.prgrms.kream.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.prgrms.kream.common.mapper.OrderMapper.*;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository repository;

	@Transactional
	public OrderCreateResponse createOrder(OrderCreateServiceRequest orderCreateServiceRequest) {
		return toOrderCreateResponse(repository.save(toOrder(orderCreateServiceRequest)));
	}
}
