package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;

@ApiModel
public record MemberUpdateRequest(

		@NotBlank(message = "이름은 빈값일 수 없습니다")
		@Length(max = 20, message = "이름은 {max}자 이내여야 합니다")
		String name,

		@NotBlank(message = "핸드폰 번호는 빈값일 수 없습니다")
		@Length(min = 11, max = 11, message = "번호는 {max}자여야 합니다")
		String phone,

		@NotBlank(message = "비밀번호는 빈값일 수 없습니다")
		@Length(min = 8, max = 16, message = "{min}자 부터 {max}자 까지 허용됩니다.")
		String password
) {
}
