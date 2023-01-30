package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

// @Builder
public record MemberUpdateRequest(
		@NotBlank(message = "비밀번호는 빈값일 수 없습니다")
		@Length(max = 20, message = "이름은 20자 이내여야 합니다")
		String name,

		@NotBlank(message = "핸드폰 번호는 빈값일 수 없습니다")
		@Length(min = 11, max = 11, message = "번호는 11자여야 합니다")
		String phone,

		@NotBlank(message = "비밀번호는 빈값일 수 없습니다")
		@Length(min = 8, max = 16, message = "비밀번호는 8 ~ 16자 이내여야 합니다")
		String password,

		MultipartFile imageFile
) {
}
