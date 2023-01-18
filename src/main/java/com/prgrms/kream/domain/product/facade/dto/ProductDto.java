package com.prgrms.kream.domain.product.facade.dto;

public record ProductDto(
		Long id,
		String name,
		int releasePrice,
		String description) {
}
