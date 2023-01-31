package com.prgrms.kream.domain.member.dto.response;

import java.util.List;

public record MemberUpdateResponse(
		Long id,
		String name,
		String phone,
		List<String> imagePaths
) {
}
