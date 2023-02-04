package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;

public record FollowingDeleteRequest(
		@ApiModelProperty(value = "팔로잉 하는 사용자 아이디", required = true, example = "1")
		@NotNull(message = "followingMemberId는 빈값 일 수 없습니다.")
		@Positive(message = "followingMemberId는 음수 일 수 없습니다.")
		Long followingMemberId,

		@ApiModelProperty(value = "팔로잉 되는 사용자 아이디", required = true, example = "2")
		@NotNull(message = "followedMemberId는 빈값 일 수 없습니다.")
		@Positive(message = "followedMemberId는 음수 일 수 없습니다.")
		Long followedMemberId
) {

}
