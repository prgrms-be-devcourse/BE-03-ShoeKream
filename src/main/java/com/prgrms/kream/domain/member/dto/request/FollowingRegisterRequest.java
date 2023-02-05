package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;

public record FollowingRegisterRequest(

		@ApiModelProperty(value = "팔로잉 하고 싶은 사용자 아이디", required = true, example = "2")
		@NotNull(message = "빈값일 수 없습니다.")
		@Positive(message = "followedMemberId는 자연수여야 합니다.")
		Long followedMemberId
) {
}
