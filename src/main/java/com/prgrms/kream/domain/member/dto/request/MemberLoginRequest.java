package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public record MemberLoginRequest(
		@ApiModelProperty(value = "사용자 이메일", required = true, example = "hello@gmail.com")
		@NotBlank(message = "email은 빈값일 수 없습니다.")
		@Email(message = "이메일 형식에 맞지 않습니다.")
		String email,

		@ApiModelProperty(value = "사용자 비밀번호", required = true, example = "Pa!12345678")
		@NotBlank(message = "password는 빈값일 수 없습니다.")
		String password
) {
}
