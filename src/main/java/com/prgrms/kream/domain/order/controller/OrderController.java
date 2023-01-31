package com.prgrms.kream.domain.order.controller;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.order.dto.request.OrderCancelRequest;
import com.prgrms.kream.domain.order.dto.request.OrderCreateFacadeRequest;
import com.prgrms.kream.domain.order.dto.request.OrderFindRequest;
import com.prgrms.kream.domain.order.dto.response.OrderCreateResponse;
import com.prgrms.kream.domain.order.dto.response.OrderFindResponse;
import com.prgrms.kream.domain.order.facade.OrderFacade;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		OrderCreateResponse orderCreateResponse = orderFacade.registerBySellingBid(orderCreateFacadeRequest);
		return ApiResponse.of(orderCreateResponse);
	}

	@PostMapping("/buying-bid")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<OrderCreateResponse> registerByBuyingBid(
			@Valid @RequestBody OrderCreateFacadeRequest orderCreateFacadeRequest
	) {
		OrderCreateResponse orderCreateResponse = orderFacade.registerByBuyingBid(orderCreateFacadeRequest);
		return ApiResponse.of(orderCreateResponse);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<OrderFindResponse> findById(@Valid @RequestBody OrderFindRequest orderFindRequest) {
		return ApiResponse.of(orderFacade.findById(orderFindRequest));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<String> deleteById(@PathVariable("id") Long id) {
		OrderCancelRequest orderCancelRequest = new OrderCancelRequest(id);
		orderFacade.deleteById(orderCancelRequest);
		return ApiResponse.of("주문이 삭제되었습니다");
	}
}
