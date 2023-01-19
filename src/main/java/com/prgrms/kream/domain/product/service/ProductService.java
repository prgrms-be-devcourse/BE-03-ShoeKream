package com.prgrms.kream.domain.product.service;

import static com.prgrms.kream.common.mapper.ProductMapper.*;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.prgrms.kream.domain.product.dto.request.ProductRegisterFacadeRequest;
import com.prgrms.kream.domain.product.dto.response.ProductGetFacadeResponse;
import com.prgrms.kream.domain.product.dto.response.ProductRegisterResponse;
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

	public ProductRegisterResponse register(ProductRegisterFacadeRequest productRegisterFacadeRequest) {
		Product product = productRepository.save(toProduct(productRegisterFacadeRequest));

		List<ProductOption> productOptions = toProductOption(productRegisterFacadeRequest.sizes(), product);
		productOptionRepository.saveAll(productOptions);

		return toProductRegisterResponse(product.getId());
	}

	public ProductGetFacadeResponse get(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("productId does not exist"));
		return toProductGetFacadeResponse(product);
	}
}
