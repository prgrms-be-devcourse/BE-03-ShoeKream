package com.prgrms.kream.domain.account.dto.request;

import javax.validation.constraints.NotNull;

public record TransactionHistoryGetFacadeRequest(
		@NotNull(message = "거래 내역을 조회하고 사는 당사자의 Id는 필수로 있어야 합니다")
		Long memberId
) {
}
