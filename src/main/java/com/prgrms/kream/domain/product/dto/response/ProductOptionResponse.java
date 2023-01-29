package com.prgrms.kream.domain.product.dto.response;

public record ProductOptionResponse(
		Long id,
		int size,
		int highestPrice,
		int lowestPrice) {
}
