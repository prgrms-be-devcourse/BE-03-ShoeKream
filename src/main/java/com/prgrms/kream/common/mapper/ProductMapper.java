package com.prgrms.kream.common.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.prgrms.kream.domain.product.controller.dto.ProductGetResponse;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterRequest;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterResponse;
import com.prgrms.kream.domain.product.facade.dto.ProductGetResponseOfFacade;
import com.prgrms.kream.domain.product.facade.dto.ProductRegisterRequestOfFacade;
import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.model.ProductOption;

public class ProductMapper {

	private ProductMapper() {
	}

	public static Product toProduct(ProductRegisterRequestOfFacade productRegisterRequestOfFacade) {
		return Product.builder()
				.name(productRegisterRequestOfFacade.name())
				.releasePrice(productRegisterRequestOfFacade.releasePrice())
				.description(productRegisterRequestOfFacade.description())
				.build();
	}

	public static ProductRegisterRequestOfFacade toProductRegisterRequestOfFacade(
			ProductRegisterRequest productRegisterRequest) {
		return new ProductRegisterRequestOfFacade(productRegisterRequest.name(), productRegisterRequest.releasePrice(),
				productRegisterRequest.description(), productRegisterRequest.sizes());
	}

	public static ProductRegisterResponse toProductRegisterResponse(Long id) {
		return new ProductRegisterResponse(id);
	}

	public static ProductGetResponseOfFacade toProductGetResponseOfFacade(Product product) {
		return new ProductGetResponseOfFacade(product.getId(), product.getName(), product.getReleasePrice(),
				product.getDescription());
	}

	public static ProductGetResponse toProductGetResponse(ProductGetResponseOfFacade productGetResponseOfFacade,
			List<String> imagePaths) {
		return new ProductGetResponse(productGetResponseOfFacade.id(), productGetResponseOfFacade.name(),
				productGetResponseOfFacade.releasePrice(),
				productGetResponseOfFacade.description(), imagePaths);
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
