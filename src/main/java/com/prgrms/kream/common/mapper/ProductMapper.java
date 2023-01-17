package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.product.controller.dto.ProductRegisterRequest;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterResponse;
import com.prgrms.kream.domain.product.facade.dto.ProductRegisterDto;
import com.prgrms.kream.domain.product.model.Product;

public class ProductMapper {

	private ProductMapper() {
	}

	public static Product toProduct(ProductRegisterDto productRegisterDto) {
		return Product.builder()
				.name(productRegisterDto.name())
				.releasePrice(productRegisterDto.releasePrice())
				.description(productRegisterDto.description())
				.build();
	}

	public static ProductRegisterDto toProductRegisterDto(ProductRegisterRequest productRegisterRequest) {
		return new ProductRegisterDto(productRegisterRequest.name(), productRegisterRequest.releasePrice(),
				productRegisterRequest.description());
	}

	public static ProductRegisterResponse toProductRegisterResponse(Long id) {
		return new ProductRegisterResponse(id);
	}
}
