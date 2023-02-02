package com.prgrms.kream.domain.coupon.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record CouponEventRegisterRequest(
		@NotNull(message = "쿠폰은 필수 입력사항 입니다.")
		@Positive(message = "잘못된 쿠폰 ID")
		Long couponId,
		@NotNull(message = "멤버는 필수 입력사항 입니다.")
		@Positive(message = "잘못된 사용자 ID")
		Long memberId
) {
}
