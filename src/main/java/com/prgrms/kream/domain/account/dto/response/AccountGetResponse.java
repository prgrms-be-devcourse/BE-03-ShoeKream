package com.prgrms.kream.domain.account.dto.response;

public record AccountGetResponse(
		Long id,
		Long memberId,
		int balance
) {
}
