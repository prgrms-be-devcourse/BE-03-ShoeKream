package com.prgrms.kream.domain.member.dto.response;

import java.util.List;

import com.prgrms.kream.domain.member.model.Authority;

import lombok.Builder;

@Builder
public record MemberGetResponse(
		Long id,
		String name,
		String email,
		String phone,
		Boolean isMale,
		Authority authority,
		List<String> imagePaths
) {
}
