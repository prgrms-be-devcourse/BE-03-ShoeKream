package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.prgrms.kream.domain.member.model.Authority;

public record MemberRegisterRequest(
		@NotBlank(message = "name은 빈값일 수 없습니다.")
		@Length(max = 20, message = "길이는 20자 이하여야 합니다.")
		String name,

		@NotBlank(message = "email은 빈값일 수 없습니다.")
		@Email(message = "이메일 형식에 맞지 않습니다.")
		@Length(max = 20)
		String email,
		@NotBlank(message = "phone은 빈값일 수 없습니다.")
		@Length(min = 11, max = 11)
		String phone,
		@NotBlank(message = "password는 빈값일 수 없습니다.")
		@Length(min = 8, max = 16)
		String password,
		@NotNull(message = "gender는 빈값일 수 없습니다.")
		Boolean isMale,
		@NotNull
		Authority authority
) {
}
