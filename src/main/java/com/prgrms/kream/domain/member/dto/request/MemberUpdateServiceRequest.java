package com.prgrms.kream.domain.member.dto.request;

public record MemberUpdateServiceRequest(
		Long id,
		String name,
		String phone,
		String password
) {
}
