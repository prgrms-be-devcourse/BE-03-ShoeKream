package com.prgrms.kream.domain.order.controller;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.order.dto.request.OrderCreateFacadeRequest;
import com.prgrms.kream.domain.order.dto.request.OrderFindRequest;
import com.prgrms.kream.domain.order.dto.response.OrderCreateResponse;
import com.prgrms.kream.domain.order.dto.response.OrderFindResponse;
import com.prgrms.kream.domain.order.facade.OrderFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("/api/v1/orders")
@Api(tags = "주문 컨트롤러")
public class OrderController {
	private final OrderFacade orderFacade;

	@PostMapping("/selling-bids")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "판매 입찰 기반 주문 생성")
	public ApiResponse<OrderCreateResponse> registerBySellingBid(
			@Valid @RequestBody
			@ApiParam(value = "판매 입찰 정보", required = true)
			OrderCreateFacadeRequest orderCreateFacadeRequest
	) {
		OrderCreateResponse orderCreateResponse = orderFacade.registerOrderBySellingBid(orderCreateFacadeRequest);
		return ApiResponse.of(orderCreateResponse);
	}

	@PostMapping("/buying-bids")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "구매 입찰 기반 주문 생성")
	public ApiResponse<OrderCreateResponse> registerByBuyingBid(
			@Valid @RequestBody
			@ApiParam(value = "구매 입찰 정보", required = true)
			OrderCreateFacadeRequest orderCreateFacadeRequest
	) {
		OrderCreateResponse orderCreateResponse = orderFacade.registerOrderByBuyingBid(orderCreateFacadeRequest);
		return ApiResponse.of(orderCreateResponse);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "주문 조회")
	public ApiResponse<OrderFindResponse> getOrder(
			@Valid @RequestBody
			@ApiParam(value = "조회할 주문 정보 목록", required = true)
			OrderFindRequest orderFindRequest) {
		return ApiResponse.of(orderFacade.getOrder(orderFindRequest));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "주문 삭제")
	public ApiResponse<String> deleteOrder(
			@PathVariable("id")
			@ApiParam(value = "삭제하고자 하는 주문 id", required = true, example = "1")
			Long id) {
		orderFacade.deleteOrder(id);
		return ApiResponse.of("주문이 삭제되었습니다");
	}
}
