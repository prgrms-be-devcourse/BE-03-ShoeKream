package com.prgrms.kream.domain.product.dto.request;

import javax.validation.constraints.Positive;

public record ProductGetAllRequest(
		Long cursorId,

		@Positive(message = "page size는 0 또는 음수일 수 없습니다.")
		int pageSize,

		String searchWord) {
}
