package com.prgrms.kream.domain.product.dto.response;

import java.util.List;

public record ProductGetFacadeResponse(
		Long id,
		String name,
		int releasePrice,
		String description,
		List<ProductOptionResponse> productOptionResponses) {
}
