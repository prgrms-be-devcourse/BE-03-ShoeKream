package com.prgrms.kream.domain.order;

import com.prgrms.kream.domain.order.dto.request.OrderCreateServiceRequest;
import com.prgrms.kream.domain.order.dto.response.OrderCreateResponse;
import com.prgrms.kream.domain.order.model.Order;
import com.prgrms.kream.domain.order.model.OrderStatus;
import com.prgrms.kream.domain.order.repository.OrderRepository;
import com.prgrms.kream.domain.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
	@Mock
	OrderRepository repository;

	@InjectMocks
	OrderService service;

	Long sellingBid = 456L;
	Long orderId = 1L;

	Long buyerId = 2L;
	Long sellerId = 3L;

	@Test
	@DisplayName("입찰 기반 주문 생성 테스트")
	void orderCreateByBidTest() {
		// Given
		OrderCreateServiceRequest orderCreateServiceRequest =
				new OrderCreateServiceRequest(orderId, buyerId, sellerId, 4L, 1500, "문 앞에 놔주세요.");
		Order order = Order.builder()
				.id(orderId)
				.buyerId(buyerId)
				.sellerId(sellerId)
				.productOptionId(4L)
				.price(1500)
				.orderStatus(OrderStatus.PAYED)
				.orderRequest("문 앞에 놔주세요.")
				.build();

		// When
		when(repository.save(any(Order.class))).thenReturn(order);
		OrderCreateResponse orderCreateResponse = service.createOrder(orderCreateServiceRequest);

		// Then
		assertThat(orderCreateResponse.id()).isEqualTo(1L);
	}
}
