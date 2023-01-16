package com.prgrms.kream.domain.product.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.service.ImageService;
import com.prgrms.kream.domain.product.dto.ProductRegisterRequest;
import com.prgrms.kream.domain.product.dto.ProductRegisterResponse;
import com.prgrms.kream.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductFacade {

	private final ProductService productService;
	private final ImageService imageService;

	public ProductRegisterResponse register(ProductRegisterRequest productRegisterRequest,
			List<MultipartFile> multipartFiles) {
		ProductRegisterResponse productRegisterResponse = productService.register(productRegisterRequest);
		imageService.register(multipartFiles, productRegisterResponse.id(), DomainType.PRODUCT);
		return productRegisterResponse;
	}
}