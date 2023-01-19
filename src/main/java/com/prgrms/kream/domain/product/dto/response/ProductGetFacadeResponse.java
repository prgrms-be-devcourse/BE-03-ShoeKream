package com.prgrms.kream.domain.product.dto.response;

public record ProductGetFacadeResponse(
		Long id,
		String name,
		int releasePrice,
		String description) {
}
