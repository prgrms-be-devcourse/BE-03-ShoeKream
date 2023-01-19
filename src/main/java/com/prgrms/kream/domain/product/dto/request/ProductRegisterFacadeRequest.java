package com.prgrms.kream.domain.product.dto.request;

import java.util.List;

public record ProductRegisterFacadeRequest(
		String name,
		int releasePrice,
		String description,
		List<Integer> sizes) {
}
