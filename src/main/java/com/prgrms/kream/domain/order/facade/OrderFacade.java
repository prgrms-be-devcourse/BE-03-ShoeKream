package com.prgrms.kream.domain.order.facade;

import static com.prgrms.kream.common.mapper.OrderMapper.*;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateOtherServiceRequest;
import com.prgrms.kream.domain.account.facade.AccountFacade;
import com.prgrms.kream.domain.account.model.TransactionType;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.request.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidFindResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.service.BuyingBidService;
import com.prgrms.kream.domain.bid.service.SellingBidService;
import com.prgrms.kream.domain.order.dto.request.OrderCancelRequest;
import com.prgrms.kream.domain.order.dto.request.OrderCreateFacadeRequest;
import com.prgrms.kream.domain.order.dto.request.OrderCreateServiceRequest;
import com.prgrms.kream.domain.order.dto.request.OrderFindRequest;
import com.prgrms.kream.domain.order.dto.request.OrderUpdateStatusRequest;
import com.prgrms.kream.domain.order.dto.response.OrderCreateResponse;
import com.prgrms.kream.domain.order.dto.response.OrderFindResponse;
import com.prgrms.kream.domain.order.dto.response.OrderUpdateStatusResponse;
import com.prgrms.kream.domain.order.model.Order;
import com.prgrms.kream.domain.order.model.OrderStatus;
import com.prgrms.kream.domain.order.service.OrderService;
import com.prgrms.kream.domain.product.service.ProductService;
import java.util.Collections;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderFacade {
	private final OrderService orderService;
	private final SellingBidService sellingBidService;
	private final BuyingBidService buyingBidService;
	private final ProductService productService;
	private final AccountFacade accountFacade;

	private final Long myId = 0L;

	@Transactional
	public OrderCreateResponse registerBySellingBid(OrderCreateFacadeRequest orderCreateFacadeRequest) {
		SellingBidFindResponse sellingBidFindResponse =
				sellingBidService.findById(new SellingBidFindRequest(
						Collections.singletonList(orderCreateFacadeRequest.bidId())));

		// TODO 자신의 ID를 가져오는 방법 생각하기
		OrderCreateServiceRequest orderCreateServiceRequest =
				new OrderCreateServiceRequest(orderCreateFacadeRequest.orderId(), orderCreateFacadeRequest.bidId(), true,
						myId, sellingBidFindResponse.memberId(),
						sellingBidFindResponse.productOptionId(), sellingBidFindResponse.price(),
						orderCreateFacadeRequest.orderRequest());

		SellingBidFindRequest sellingBidFindRequest =
				new SellingBidFindRequest(Collections.singletonList(orderCreateFacadeRequest.bidId()));

		sellingBidService.deleteById(sellingBidFindResponse.id());

		SellingBidFindResponse lowestSellingBid = null;
		try {
			lowestSellingBid =
					sellingBidService.findLowestSellingBidByProductOptionId(sellingBidFindRequest);
		} catch (EntityNotFoundException e) {
			productService.updateLowestPrice(sellingBidFindResponse.productOptionId(), 0);
		}

		productService.updateLowestPrice(sellingBidFindResponse.productOptionId(), lowestSellingBid.price());

		AccountUpdateOtherServiceRequest accountUpdateOtherServiceRequest =
				new AccountUpdateOtherServiceRequest(orderCreateServiceRequest.buyerId(), TransactionType.DEPOSIT,
						orderCreateServiceRequest.price());
		accountFacade.updateBalance(accountUpdateOtherServiceRequest);

		return orderService.register(orderCreateServiceRequest);
	}

	@Transactional
	public OrderCreateResponse registerByBuyingBid(OrderCreateFacadeRequest orderCreateFacadeRequest) {
		BuyingBidFindResponse buyingBidFindResponse =
				buyingBidService.findById(new BuyingBidFindRequest(
						Collections.singletonList(orderCreateFacadeRequest.bidId())));

		// TODO 자신의 ID를 가져오는 방법 생각하기
		OrderCreateServiceRequest orderCreateServiceRequest =
				new OrderCreateServiceRequest(orderCreateFacadeRequest.orderId(), orderCreateFacadeRequest.bidId(),
						false,
						buyingBidFindResponse.memberId(), myId,
						buyingBidFindResponse.productOptionId(), buyingBidFindResponse.price(),
						orderCreateFacadeRequest.orderRequest());

		buyingBidService.deleteById(buyingBidFindResponse.id());

		BuyingBidFindRequest buyingBidFindRequest =
				new BuyingBidFindRequest(Collections.singletonList(orderCreateFacadeRequest.bidId()));

		BuyingBidFindResponse highestBuyingBid = null;
		try {
			highestBuyingBid =
					buyingBidService.findHighestBuyingBidByProductOptionId(buyingBidFindRequest);
		} catch (EntityNotFoundException e) {
			productService.updateHighestPrice(buyingBidFindResponse.productOptionId(), 0);
		}

		productService.updateHighestPrice(buyingBidFindResponse.productOptionId(), highestBuyingBid.price());

		AccountUpdateOtherServiceRequest accountUpdateOtherServiceRequest =
				new AccountUpdateOtherServiceRequest(orderCreateServiceRequest.buyerId(), TransactionType.DEPOSIT,
						orderCreateServiceRequest.price());
		accountFacade.updateBalance(accountUpdateOtherServiceRequest);

		return orderService.register(orderCreateServiceRequest);
	}

	@Transactional(readOnly = true)
	public OrderFindResponse findById(OrderFindRequest orderFindRequest) {
		return orderService.findById(orderFindRequest);
	}

	@Transactional
	public void deleteById(Long id) {
		OrderCancelRequest orderCancelRequest = new OrderCancelRequest(id);
		OrderFindRequest orderFindRequest = new OrderFindRequest(id);
		Order order = toOrder(orderService.findById(orderFindRequest));

		// todo 주문 취소시 패널티를 준다(거래 금지 일시 or 수수료)
		if (order.getIsBasedOnSellingBid()) {
			SellingBidFindResponse sellingBidFindResponse =
					sellingBidService.findById(new SellingBidFindRequest(Collections.singletonList(order.getBidId())));
			sellingBidService.restoreById(order.getBidId());
			productService.compareLowestPrice(order.getProductOptionId(), sellingBidFindResponse.price());
		} else {
			BuyingBidFindResponse buyingBidFindResponse =
					buyingBidService.findById(new BuyingBidFindRequest(Collections.singletonList(order.getBidId())));
			buyingBidService.restoreById(order.getBidId());
			productService.compareHighestPrice(order.getProductOptionId(), buyingBidFindResponse.price());
		}

		orderService.deleteById(orderCancelRequest);
	}

	@Transactional
	public OrderUpdateStatusResponse updateOrderStatus(OrderUpdateStatusRequest orderUpdateStatusRequest) {
		OrderUpdateStatusResponse orderUpdateStatusResponse =
				orderService.updateStatus(orderUpdateStatusRequest);
		if (orderUpdateStatusResponse.orderStatus() == OrderStatus.ORDER_CONFIRMED) {
			AccountUpdateOtherServiceRequest accountUpdateOtherServiceRequest =
					new AccountUpdateOtherServiceRequest(
							orderService.findById(new OrderFindRequest(orderUpdateStatusRequest.id())).sellerId(),
							TransactionType.DEPOSIT,
							orderService.findById(new OrderFindRequest(orderUpdateStatusRequest.id())).price()
					);
			accountFacade.updateBalance(accountUpdateOtherServiceRequest);
		}
		return orderService.updateStatus(orderUpdateStatusRequest);
	}
}
