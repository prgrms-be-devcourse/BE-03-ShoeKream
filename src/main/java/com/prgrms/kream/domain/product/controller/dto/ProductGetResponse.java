package com.prgrms.kream.domain.product.controller.dto;

import java.util.List;

public record ProductGetResponse(
		Long id,
		String name,
		int releasePrice,
		String description,
		List<String> imagePaths) {
}
