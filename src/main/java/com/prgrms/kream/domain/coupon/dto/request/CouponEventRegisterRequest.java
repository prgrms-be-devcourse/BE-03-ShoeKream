package com.prgrms.kream.domain.coupon.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("발급할 쿠폰과 사용자 정보")
public record CouponEventRegisterRequest(
		@Positive(message = "잘못된 쿠폰 Id")
		@NotNull(message = "쿠폰은 필수 입력사항 입니다.")
		@ApiModelProperty(value = "쿠폰 아이디", example = "1")
		Long couponId,
		@Positive(message = "잘못된 사용자 Id")
		@NotNull(message = "멤버는 필수 입력사항 입니다.")
		@ApiModelProperty(value = "사용자 아이디", example = "1")
		Long memberId
) {
}
