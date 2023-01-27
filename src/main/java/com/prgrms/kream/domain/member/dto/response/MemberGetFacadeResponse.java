package com.prgrms.kream.domain.member.dto.response;

import com.prgrms.kream.domain.member.model.Authority;

import lombok.Builder;

@Builder
public record MemberGetFacadeResponse(
		Long id,
		String name,
		String email,
		String phone,
		String password,
		Boolean isMale,
		Authority authority
) {
}
