package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel
public record MemberUpdateRequest(

		@ApiParam(value = "수정 요청 정보", required = true)
		// @ApiModelProperty(value = "사용자 이름", required = true, example = "홍길동")
		@NotBlank(message = "이름은 빈값일 수 없습니다")
		@Length(max = 20, message = "이름은 20자 이내여야 합니다")
		String name,

		@ApiParam(value = "수정 요청 정보", required = true)
		// @ApiModelProperty(value = "사용자 번호", required = true, example = "01012345678")
		@NotBlank(message = "핸드폰 번호는 빈값일 수 없습니다")
		@Length(min = 11, max = 11, message = "번호는 11자여야 합니다")
		String phone,

		@ApiParam(value = "수정 요청 정보", required = true)
		// @ApiModelProperty(value = "사용자 비밀번호", required = true, example = "01012345678")
		@NotBlank(message = "비밀번호는 빈값일 수 없습니다")
		@Length(min = 8, max = 16, message = "비밀번호는 8 ~ 16자 이내여야 합니다")
		String password

		// @ApiParam(value = "test", required = false)
		// @RequestPart(value = "image", required = false)
		// @ApiModelProperty(value = "사용자 프로필 이미지", required = false, dataType = "Multipart")
		// MultipartFile imageFile
) {
}
