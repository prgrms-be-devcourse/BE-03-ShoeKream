package com.prgrms.kream.domain.bid.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public record SellingBidFindRequest(
		@NotNull(message = "찾고자 하는 ID를 입력해주세요")
		List<Long> ids
) {
}
