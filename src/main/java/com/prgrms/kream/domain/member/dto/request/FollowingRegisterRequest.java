package com.prgrms.kream.domain.member.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record FollowingRegisterRequest(
		@NotNull(message = "빈값일 수 없습니다.")
		@Positive(message = "followedMemberId는 자연수여야 합니다.")
		Long followedMemberId
) {
}
