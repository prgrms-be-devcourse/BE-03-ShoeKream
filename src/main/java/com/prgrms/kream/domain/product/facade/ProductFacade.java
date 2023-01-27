package com.prgrms.kream.domain.product.facade;

import static com.prgrms.kream.common.mapper.ProductMapper.*;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.service.ImageService;
import com.prgrms.kream.domain.product.dto.request.ProductGetAllRequest;
import com.prgrms.kream.domain.product.dto.request.ProductRegisterRequest;
import com.prgrms.kream.domain.product.dto.request.ProductUpdateRequest;
import com.prgrms.kream.domain.product.dto.response.ProductGetAllResponses;
import com.prgrms.kream.domain.product.dto.response.ProductGetFacadeResponse;
import com.prgrms.kream.domain.product.dto.response.ProductGetResponse;
import com.prgrms.kream.domain.product.dto.response.ProductRegisterResponse;
import com.prgrms.kream.domain.product.dto.response.ProductUpdateResponse;
import com.prgrms.kream.domain.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductFacade {

	private final ProductService productService;
	private final ImageService imageService;

	@CacheEvict(value = "products", allEntries = true)
	@Transactional
	public ProductRegisterResponse register(ProductRegisterRequest productRegisterRequest) {
		ProductRegisterResponse productRegisterResponse
				= productService.register(toProductRegisterFacadeRequest(productRegisterRequest));
		imageService.register(productRegisterRequest.images(), productRegisterResponse.id(), DomainType.PRODUCT);
		return productRegisterResponse;
	}

	@Cacheable(cacheNames = "product", key = "#productId")
	@Transactional(readOnly = true)
	public ProductGetResponse get(Long productId) {
		ProductGetFacadeResponse productGetFacadeResponse = productService.get(productId);
		List<String> imagePaths = imageService.getAll(productId, DomainType.PRODUCT);
		return toProductGetResponse(productGetFacadeResponse, imagePaths);
	}

	@Cacheable(cacheNames = "products", key = "#productGetAllRequest")
	@Transactional(readOnly = true)
	public ProductGetAllResponses getAll(ProductGetAllRequest productGetAllRequest) {
		return productService.getAll(productGetAllRequest);
	}

	@Transactional
	public ProductUpdateResponse update(ProductUpdateRequest productUpdateRequest) {
		imageService.deleteAllByReference(productUpdateRequest.id(), DomainType.PRODUCT);
		imageService.register(productUpdateRequest.images(), productUpdateRequest.id(), DomainType.PRODUCT);
		return productService.update(toProductUpdateFacadeRequest(productUpdateRequest));
	}

	@Transactional
	public void delete(Long id) {
		imageService.deleteAllByReference(id, DomainType.PRODUCT);
		productService.delete(id);
	}
}
