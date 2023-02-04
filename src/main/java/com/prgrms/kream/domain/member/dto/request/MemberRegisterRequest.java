package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.prgrms.kream.domain.member.model.Authority;

import io.swagger.annotations.ApiModelProperty;

public record MemberRegisterRequest(
		@ApiModelProperty(value = "사용자 이름", required = true, example = "홍길동")
		@NotBlank(message = "name은 빈값일 수 없습니다.")
		@Length(max = 20, message = "길이는 20자 이하여야 합니다.")
		String name,

		@ApiModelProperty(value = "사용자 이메일", required = true, example = "hello@gmail.com")
		@NotBlank(message = "email은 빈값일 수 없습니다.")
		@Email(message = "이메일 형식에 맞지 않습니다.")
		@Length(max = 20)
		String email,

		@ApiModelProperty(value = "사용자 핸드폰 번호", required = true, example = "01012345678")
		@NotBlank(message = "phone은 빈값일 수 없습니다.")
		@Length(min = 11, max = 11)
		String phone,

		@ApiModelProperty(value = "사용자 비밀번호", required = true, example = "Pa!12345678")
		@NotBlank(message = "password는 빈값일 수 없습니다.")
		@Length(min = 8, max = 16)
		String password,

		@ApiModelProperty(value = "남성", required = true, example = "true")
		@NotNull(message = "gender는 빈값일 수 없습니다.")
		Boolean isMale,

		@ApiModelProperty(value = "사용자 권한", required = true, example = "ROLE_USER")
		@NotNull(message = "authority는 빈값일 수 없습니다.")
		Authority authority
) {
}
