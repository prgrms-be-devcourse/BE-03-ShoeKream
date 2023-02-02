package com.prgrms.kream.domain.account.dto.request;

import javax.validation.constraints.NotNull;

public record AccountCreateRequest(
		@NotNull(message = "계좌의 ID는 필수 입력 값입니다")
		Long id,
		@NotNull(message = "계좌 주인의 ID는 필수 입력 값입니다")
		Long memberId
) {
}
