package com.prgrms.kream.domain.bid.dto;

import java.util.List;

public record SellingBidFindRequest(
		List<Long> ids
) {
}
