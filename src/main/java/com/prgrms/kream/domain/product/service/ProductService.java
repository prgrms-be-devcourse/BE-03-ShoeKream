package com.prgrms.kream.domain.product.service;

import static com.prgrms.kream.common.mapper.ProductMapper.*;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.product.dto.ProductRegisterRequest;
import com.prgrms.kream.domain.product.dto.ProductRegisterResponse;
import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public ProductRegisterResponse register(ProductRegisterRequest productRegisterRequest) {
		Product product = productRepository.save(toProduct(productRegisterRequest));
		return toProductRegisterDto(product.getId());
	}
}