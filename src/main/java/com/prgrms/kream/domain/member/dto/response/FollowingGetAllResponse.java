package com.prgrms.kream.domain.member.dto.response;

import java.util.List;

public record FollowingGetAllResponse(
		List<Long> FollowedMemberIds
) {
}
