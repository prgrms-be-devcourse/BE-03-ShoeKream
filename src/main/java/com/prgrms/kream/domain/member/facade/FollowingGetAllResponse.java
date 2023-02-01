package com.prgrms.kream.domain.member.facade;

import java.util.List;

public record FollowingGetAllResponse(
		List<Long> FollowedMemberIds
) {
}
