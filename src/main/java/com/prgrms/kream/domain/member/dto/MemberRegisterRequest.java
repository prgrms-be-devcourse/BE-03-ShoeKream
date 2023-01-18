package com.prgrms.kream.domain.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.prgrms.kream.domain.member.model.Authority;

public record MemberRegisterRequest(
		@NotBlank
		String name,
		@Email
		String email,
		@NotBlank
		@Length(min = 11, max = 11)
		String phone,
		@NotBlank
		@Length(min = 8, max = 16)
		String password,
		@NotNull
		Boolean isMale,
		@NotNull
		Authority authority
) {
}
