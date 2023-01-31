package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

public record DeliveryInfoUpdateRequest(
		@Positive(message = "deliveryInfoId는 음수일 수 없습니다.")
		Long deliveryInfoId,

		@NotBlank(message = "이름은 빈값일 수 없습니다.")
		@Length(max = 20, message = "이름은 20자까지만 허용됩니다.")
		String name,

		@NotBlank(message = "이름은 빈값일 수 없습니다.")
		@Length(min = 11, max = 11, message = "핸드폰번호는11자여야 합니다.")
		String phone,

		@Length(min = 5, max = 5, message = "우편번호는 5자여야 합니다.")
		String postCode,

		@Length(max = 30, message = "주소는 30자 이하여야 합니다.")
		String address,

		@Length(max = 30, message = "상세 주소는 30자 이하여야 합니다.")
		String detail,

		@Positive(message = "memberId는 음수가 될 수 없습니다.")
		Long memberId
) {
}
