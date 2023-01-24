package com.prgrms.kream.domain.product.dto.response;

public record ProductGetAllResponse(
		Long id,
		String name,
		int releasePrice,
		String description) {
}
