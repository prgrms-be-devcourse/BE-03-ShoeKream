package com.prgrms.kream.domain.product.service;

import static com.prgrms.kream.common.mapper.ProductMapper.*;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.product.controller.dto.ProductRegisterResponse;
import com.prgrms.kream.domain.product.facade.dto.ProductRegisterDto;

import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public ProductRegisterResponse register(ProductRegisterDto productRegisterDto) {
		Product product = productRepository.save(toProduct(productRegisterDto));
		return toProductRegisterResponse(product.getId());
	}
}
