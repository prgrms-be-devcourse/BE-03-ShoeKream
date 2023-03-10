package com.prgrms.kream.domain.member.dto.response;

import java.util.List;

import lombok.Builder;

@Builder
public record MemberGetResponse(
		Long id,
		String name,
		String email,
		String phone,
		List<String> imagePaths
) {
}
