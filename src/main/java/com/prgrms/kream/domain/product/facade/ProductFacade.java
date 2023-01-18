package com.prgrms.kream.domain.product.facade;

import static com.prgrms.kream.common.mapper.ProductMapper.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.service.ImageService;
import com.prgrms.kream.domain.product.controller.dto.ProductGetResponse;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterRequest;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterResponse;
import com.prgrms.kream.domain.product.facade.dto.ProductGetResponseOfFacade;
import com.prgrms.kream.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductFacade {

	private final ProductService productService;
	private final ImageService imageService;

	public ProductRegisterResponse register(ProductRegisterRequest productRegisterRequest) {
		ProductRegisterResponse productRegisterResponse
				= productService.register(toProductRegisterRequestOfFacade(productRegisterRequest));
		imageService.register(productRegisterRequest.images(), productRegisterResponse.id(), DomainType.PRODUCT);
		return productRegisterResponse;
	}

	public ProductGetResponse get(Long productId) {
		ProductGetResponseOfFacade productGetResponseOfFacade = productService.get(productId);
		List<String> imagePaths = imageService.getAll(productId, DomainType.PRODUCT);
		return toProductGetResponse(productGetResponseOfFacade, imagePaths);
	}
}
