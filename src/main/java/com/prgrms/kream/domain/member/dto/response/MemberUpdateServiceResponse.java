package com.prgrms.kream.domain.member.dto.response;

public record MemberUpdateServiceResponse(
		Long id,
		String name,
		String phone
) {
}
