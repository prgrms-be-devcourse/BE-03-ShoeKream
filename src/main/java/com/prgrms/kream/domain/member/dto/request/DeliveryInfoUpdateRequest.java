package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;

public record DeliveryInfoUpdateRequest(
		@ApiModelProperty(value = "수정하고자 하는 배송정보 아이디", required = true, example = "1")
		@NotNull(message = "deliveryId는 반드시 있어야 합니다")
		@Positive(message = "deliveryInfoId는 음수일 수 없습니다")
		Long deliveryInfoId,

		@ApiModelProperty(value = "수령 받는 사람의 이름", required = true, example = "홍길동")
		@NotBlank(message = "이름은 빈값일 수 없습니다")
		@Length(max = 20, message = "이름은 20자까지만 허용됩니다")
		String name,

		@ApiModelProperty(value = "수령인 핸드폰 번호", required = true, example = "01012345678")
		@NotBlank(message = "핸드폰 번호는 빈값일 수 없습니다")
		@Length(min = 11, max = 11, message = "핸드폰번호는 11자여야 합니다")
		String phone,

		@ApiModelProperty(value = "우편번호", required = true, example = "12345")
		@NotBlank(message = "우편번호는 빈값일 수 없습니다")
		@Length(min = 5, max = 5, message = "우편번호는 5자여야 합니다")
		String postCode,

		@ApiModelProperty(value = "주소", required = true, example = "서울시 강동구~")
		@NotBlank(message = "주소는 빈값일 수 없습니다")
		@Length(max = 30, message = "주소는 30자 이하여야 합니다")
		String address,

		@ApiModelProperty(value = "상세주소", required = true, example = "101호")
		@NotBlank(message = "상세주소는 빈값일 수 없습니다")
		@Length(max = 30, message = "상세 주소는 30자 이하여야 합니다")
		String detail,

		@ApiModelProperty(value = "사용자 아이디", required = true, example = "1")
		@NotNull(message = "memberId는 반드시 있어야 합니다")
		@Positive(message = "memberId는 음수가 될 수 없습니다")
		Long memberId
) {
}
