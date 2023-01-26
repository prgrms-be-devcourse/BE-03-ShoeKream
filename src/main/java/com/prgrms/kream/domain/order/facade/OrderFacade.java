package com.prgrms.kream.domain.order.facade;

import com.prgrms.kream.domain.bid.dto.request.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.service.SellingBidService;
import com.prgrms.kream.domain.order.dto.request.OrderCreateFacadeRequest;
import com.prgrms.kream.domain.order.dto.request.OrderCreateServiceRequest;
import com.prgrms.kream.domain.order.dto.response.OrderCreateResponse;
import com.prgrms.kream.domain.order.service.OrderService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderFacade {
	private final OrderService orderService;
	private final SellingBidService sellingBidService;

	@Transactional
	public OrderCreateResponse createOrderBySellingBid(OrderCreateFacadeRequest orderCreateFacadeRequest) {
		SellingBidFindResponse sellingBidFindResponse =
				sellingBidService.findOneSellingBidById(new SellingBidFindRequest(
						Collections.singletonList(orderCreateFacadeRequest.sellingBidId())));

		// TODO 자신의 ID를 가져오는 방법 생각하기
		OrderCreateServiceRequest orderCreateServiceRequest =
				new OrderCreateServiceRequest(orderCreateFacadeRequest.orderId(), 0L, sellingBidFindResponse.memberId(),
						sellingBidFindResponse.productOptionId(), sellingBidFindResponse.price(),
						orderCreateFacadeRequest.orderRequest());

		sellingBidService.deleteOneSellingBidById(sellingBidFindResponse.id());

		return orderService.createOrder(orderCreateServiceRequest);
	}
}
