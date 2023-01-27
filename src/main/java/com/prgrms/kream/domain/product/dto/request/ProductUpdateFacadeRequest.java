package com.prgrms.kream.domain.product.dto.request;

import java.util.List;

public record ProductUpdateFacadeRequest(
		Long id,
		int releasePrice,
		String description,
		List<Integer> sizes) {
}
