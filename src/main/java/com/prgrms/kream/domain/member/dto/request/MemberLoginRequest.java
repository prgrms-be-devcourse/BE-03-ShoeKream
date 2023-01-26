package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record MemberLoginRequest(
		@NotBlank
		@Email
		String email,

		@NotBlank
		String password
) {
}
