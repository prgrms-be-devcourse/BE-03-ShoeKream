package com.prgrms.kream.common.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.prgrms.kream.domain.product.controller.dto.ProductGetResponse;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterRequest;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterResponse;
import com.prgrms.kream.domain.product.facade.dto.ProductGetFacadeResponse;
import com.prgrms.kream.domain.product.facade.dto.ProductRegisterFacadeRequest;
import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.model.ProductOption;

public class ProductMapper {

	private ProductMapper() {
	}

	public static Product toProduct(ProductRegisterFacadeRequest productRegisterFacadeRequest) {
		return Product.builder()
				.name(productRegisterFacadeRequest.name())
				.releasePrice(productRegisterFacadeRequest.releasePrice())
				.description(productRegisterFacadeRequest.description())
				.build();
	}

	public static ProductRegisterFacadeRequest toProductRegisterFacadeRequest(
			ProductRegisterRequest productRegisterRequest) {
		return new ProductRegisterFacadeRequest(productRegisterRequest.name(), productRegisterRequest.releasePrice(),
				productRegisterRequest.description(), productRegisterRequest.sizes());
	}

	public static ProductRegisterResponse toProductRegisterResponse(Long id) {
		return new ProductRegisterResponse(id);
	}

	public static ProductGetFacadeResponse toProductGetFacadeResponse(Product product) {
		return new ProductGetFacadeResponse(product.getId(), product.getName(), product.getReleasePrice(),
				product.getDescription());
	}

	public static ProductGetResponse toProductGetResponse(
			ProductGetFacadeResponse productGetFacadeResponse, List<String> imagePaths) {
		return new ProductGetResponse(productGetFacadeResponse.id(), productGetFacadeResponse.name(),
				productGetFacadeResponse.releasePrice(), productGetFacadeResponse.description(), imagePaths);
	}

	public static List<ProductOption> toProductOption(List<Integer> sizes, Product product) {
		return sizes.stream()
				.map(size ->
						ProductOption.builder()
								.size(size)
								.product(product)
								.build())
				.collect(Collectors.toList());
	}
}
