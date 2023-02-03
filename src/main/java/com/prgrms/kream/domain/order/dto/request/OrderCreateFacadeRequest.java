package com.prgrms.kream.domain.order.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@ApiModel("주문 생성 요청")
public record OrderCreateFacadeRequest(
		@ApiModelProperty(value = "주문 id", required = true, example = "1")
		@NotNull(message = "주문 ID는 필수 입력 사항입니다")
		Long orderId,
		@ApiModelProperty(value = "입찰 id", required = true, example = "2")
		@NotNull(message = "주문의 대상이 되는 입찰의 ID는 필수 입력사항압니다")
		Long bidId,

		@ApiModelProperty(value = "기반된 주문의 판매 입찰인지 여부", required = true, example = "true")
		@NotNull(message = "입찰의 구매, 판매 정보 입력을 필수입니다.")
		boolean isBasedOnSellingBid,
		@ApiModelProperty(value = "주문 상태", required = true, example = "PAYED")
		@Length(max = 50, message = "주문 요청사항은 최대로 50자 까지만 입력 가능합니다")
		String orderRequest
) {
}
