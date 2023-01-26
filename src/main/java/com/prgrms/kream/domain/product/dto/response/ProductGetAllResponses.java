package com.prgrms.kream.domain.product.dto.response;

import java.util.List;

public record ProductGetAllResponses(
		List<ProductGetAllResponse> productGetAllResponses,
		Long lastId) {
}
