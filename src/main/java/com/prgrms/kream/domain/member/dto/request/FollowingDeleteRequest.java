package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record FollowingDeleteRequest(
		@NotNull(message = "followingMemberId는 빈값 일 수 없습니다.")
		@Positive(message = "followingMemberId는 음수 일 수 없습니다.")
		Long followingMemberId,

		@NotNull(message = "followedMemberId는 빈값 일 수 없습니다.")
		@Positive(message = "followedMemberId는 음수 일 수 없습니다.")
		Long followedMemberId
) {

}
