package com.prgrms.kream.domain.coupon.dto.request;

import javax.validation.constraints.NotNull;

public record CouponEventRegisterRequest(
		@NotNull(message = "쿠폰은 필수 입력사항 입니다.")
		long coupon_id,
		@NotNull(message = "멤버는 필수 입력사항 입니다.")
		long member_id
) {
}
