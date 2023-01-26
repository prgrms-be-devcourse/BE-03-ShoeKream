package com.prgrms.kream.domain.order.controller;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.order.dto.request.OrderCreateFacadeRequest;
import com.prgrms.kream.domain.order.dto.response.OrderCreateResponse;
import com.prgrms.kream.domain.order.facade.OrderFacade;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
	private final OrderFacade orderFacade;

	@PostMapping("/selling-bid")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<OrderCreateResponse> registerBySellingBid(
			@Valid @RequestBody OrderCreateFacadeRequest orderCreateFacadeRequest
	) {
		OrderCreateResponse orderCreateResponse = orderFacade.createOrderBySellingBid(orderCreateFacadeRequest);
		return ApiResponse.of(orderCreateResponse);
	}

	@PostMapping("/buying-bid")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<OrderCreateResponse> registerByBuyingBid(
			@Valid @RequestBody OrderCreateFacadeRequest orderCreateFacadeRequest
	) {
		OrderCreateResponse orderCreateResponse = orderFacade.createOrderByBuyingBid(orderCreateFacadeRequest);
		return ApiResponse.of(orderCreateResponse);
	}
}
