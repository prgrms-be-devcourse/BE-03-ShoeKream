package com.prgrms.kream.domain.order.dto.request;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record OrderCreateFacadeRequest(
		@NotNull(message = "주문 ID는 필수 입력 사항입니다")
		Long orderId,
		@NotNull(message = "주문의 대상이 되는 입찰의 ID는 필수 입력사항압니다")
		Long bidId,

		@NotNull(message = "입찰의 구매, 판매 정보 입력을 필수입니다.")
		boolean isBasedOnSellingBid,
		@Length(max = 50, message = "주문 요청사항은 최대로 50자 까지만 입력 가능합니다")
		String orderRequest
) {
}
