package com.prgrms.kream.domain.order.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.Length;

public record OrderCreateServiceRequest(

		@NotNull(message = "ID는 필수적으로 있어야 합니다")
		Long id,

		@NotNull(message = "주문의 기반이 된 입찰의 정보를 필수입니다")
		Long bidId,

		@NotNull(message = "입찰의 구매, 판매 정보 입력을 필수입니다.")
		boolean isBasedOnSellingBid,

		@NotNull(message = "구매자의 ID는 필수 입력사항입니다")
		Long buyerId,
		@NotNull(message = "판매자의 ID는 필수 입력사항입니다")
		Long sellerId,

		@NotNull(message = "거래 품목의 ID는 필수 입력사항입니다")
		Long productOptionId,

		@NotNull(message = "가격은 필수 입력사항입니다")
		@PositiveOrZero
		int price,

		@Length(max = 50, message = "주문 요청사항은 최대 50자 까지만 입력 가능합니다")
		String orderRequest
) {
}
