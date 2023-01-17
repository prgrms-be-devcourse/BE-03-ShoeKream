package com.prgrms.kream.domain.product.facade;

import static com.prgrms.kream.common.mapper.ProductMapper.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.service.ImageService;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterRequest;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterResponse;

import com.prgrms.kream.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductFacade {

	private final ProductService productService;
	private final ImageService imageService;

	@Transactional
	public ProductRegisterResponse register(ProductRegisterRequest productRegisterRequest) {
		ProductRegisterResponse productRegisterResponse
				= productService.register(toProductRegisterDto(productRegisterRequest));
		imageService.register(productRegisterRequest.images(), productRegisterResponse.id(), DomainType.PRODUCT);
		return productRegisterResponse;
	}
}
