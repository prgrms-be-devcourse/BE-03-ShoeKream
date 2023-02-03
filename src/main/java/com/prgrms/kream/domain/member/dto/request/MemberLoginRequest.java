package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record MemberLoginRequest(
		@NotBlank(message = "email은 빈값일 수 없습니다.")
		@Email(message = "이메일 형식에 맞지 않습니다.")
		String email,

		@NotBlank(message = "password는 빈값일 수 없습니다.")
		String password
) {
}
