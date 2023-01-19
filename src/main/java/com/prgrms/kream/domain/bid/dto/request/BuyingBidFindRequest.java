package com.prgrms.kream.domain.bid.dto.request;

import java.util.List;

import javax.validation.constraints.NotNull;

public record BuyingBidFindRequest(
		@NotNull(message = "찾고자 하는 ID를 입력해주세요")
		List<Long> ids
) {
}
