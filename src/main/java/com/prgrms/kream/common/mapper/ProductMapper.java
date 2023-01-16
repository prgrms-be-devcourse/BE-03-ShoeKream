package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.product.dto.ProductRegisterRequest;
import com.prgrms.kream.domain.product.dto.ProductRegisterResponse;
import com.prgrms.kream.domain.product.model.Product;

public class ProductMapper {

	private ProductMapper() {
	}

	public static Product toProduct(ProductRegisterRequest productRegisterRequest) {
		return Product.builder()
				.name(productRegisterRequest.name())
				.releasePrice(productRegisterRequest.releasePrice())
				.description(productRegisterRequest.description())
				.build();
	}

	public static ProductRegisterResponse toProductRegisterDto(Long id) {
		return new ProductRegisterResponse(id);
	}
}