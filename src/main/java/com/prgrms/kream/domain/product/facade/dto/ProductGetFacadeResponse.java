package com.prgrms.kream.domain.product.facade.dto;

public record ProductGetFacadeResponse(
		Long id,
		String name,
		int releasePrice,
		String description) {
}
