package com.prgrms.kream.domain.product.facade.dto;

public record ProductGetResponseOfFacade(
		Long id,
		String name,
		int releasePrice,
		String description) {
}
