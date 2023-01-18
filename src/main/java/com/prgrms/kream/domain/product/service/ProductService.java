package com.prgrms.kream.domain.product.service;

import static com.prgrms.kream.common.mapper.ProductMapper.*;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.product.controller.dto.ProductRegisterResponse;
import com.prgrms.kream.domain.product.facade.dto.ProductGetResponseOfFacade;
import com.prgrms.kream.domain.product.facade.dto.ProductRegisterRequestOfFacade;
import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.model.ProductOption;
import com.prgrms.kream.domain.product.repository.ProductOptionRepository;
import com.prgrms.kream.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductOptionRepository productOptionRepository;

	public ProductRegisterResponse register(ProductRegisterRequestOfFacade productRegisterRequestOfFacade) {
		Product product = productRepository.save(toProduct(productRegisterRequestOfFacade));

		List<ProductOption> productOptions = toProductOption(productRegisterRequestOfFacade.sizes(), product);
		productOptionRepository.saveAll(productOptions);

		return toProductRegisterResponse(product.getId());
	}

	public ProductGetResponseOfFacade get(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("productId does not exist"));
		return toProductGetResponseOfFacade(product);
	}
}
