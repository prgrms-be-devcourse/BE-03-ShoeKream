package com.prgrms.kream.domain.product.dto.response;

import java.util.List;

public record ProductGetResponse(
		Long id,
		String name,
		int releasePrice,
		String description,
		List<ProductOptionResponse> productOptions,
		List<String> imagePaths) {
}
