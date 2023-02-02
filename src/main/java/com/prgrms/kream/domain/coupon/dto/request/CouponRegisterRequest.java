package com.prgrms.kream.domain.coupon.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

public record CouponRegisterRequest(
		@Min(value = 0, message = "쿠폰 할인율은 {value}이상이어야 합니다.")
		@Max(value = 100, message = "쿠폰 할인율은 {value}이하여야 합니다.")
		@NotNull(message = "쿠폰 할인율은 필수 입력사항 입니다.")
		int discountValue,
		@NotBlank(message = "쿠폰 이름은 필수 입력사항 입니다.")
		@Size(max = 20, message = "쿠폰 이름은 {max}글자 이하로 입력할 수 있습니다.")
		String name,
		@PositiveOrZero(message = "쿠폰 수량은 0이상이어야 합니다.")
		@NotNull(message = "쿠폰 수량은 필수 입력사항 입니다.")
		int amount
) {
}
