package com.prgrms.kream.domain.product.facade.dto;

import java.util.List;

public record ProductRegisterFacadeRequest(
		String name,
		int releasePrice,
		String description,
		List<Integer> sizes) {
}
