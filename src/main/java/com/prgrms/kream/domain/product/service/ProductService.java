package com.prgrms.kream.domain.product.service;

import static com.prgrms.kream.common.mapper.ProductMapper.*;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.product.dto.request.ProductGetAllRequest;
import com.prgrms.kream.domain.product.dto.request.ProductRegisterFacadeRequest;
import com.prgrms.kream.domain.product.dto.request.ProductUpdateFacadeRequest;
import com.prgrms.kream.domain.product.dto.response.ProductGetAllResponses;
import com.prgrms.kream.domain.product.dto.response.ProductGetFacadeResponse;
import com.prgrms.kream.domain.product.dto.response.ProductRegisterResponse;
import com.prgrms.kream.domain.product.dto.response.ProductUpdateResponse;
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

	@Transactional
	public ProductRegisterResponse register(ProductRegisterFacadeRequest productRegisterFacadeRequest) {
		Product product = productRepository.save(toProduct(productRegisterFacadeRequest));

		productOptionRepository.saveAllBulk(
				toProductOptions(productRegisterFacadeRequest.sizes(), product)
		);

		return toProductRegisterResponse(product.getId());
	}

	@Transactional(readOnly = true)
	public ProductGetFacadeResponse get(Long productId) {
		Product product = findProductEntity(productId);
		List<ProductOption> productOptions = productOptionRepository.findAllByProduct(product);
		return toProductGetFacadeResponse(product, productOptions);
	}

	@Transactional(readOnly = true)
	public ProductGetAllResponses getAll(ProductGetAllRequest productGetAllRequest) {
		List<Product> products = productRepository.findAllByCursor(
				productGetAllRequest.cursorId(), productGetAllRequest.pageSize(), productGetAllRequest.searchWord());

		Long lastId = -1L;
		if (products.size() != 0) {
			lastId = products.get(products.size() - 1).getId();
		}

		return toProductGetAllResponses(products, lastId);
	}

	@Transactional
	public ProductUpdateResponse update(ProductUpdateFacadeRequest productFacadeUpdateRequest) {
		Product product = findProductEntity(productFacadeUpdateRequest.id());
		product.update(productFacadeUpdateRequest.releasePrice(), productFacadeUpdateRequest.description());

		productOptionRepository.deleteAllByProductId(product.getId());
		productOptionRepository.saveAllBulk(
				toProductOptions(productFacadeUpdateRequest.sizes(), product)
		);

		return toProductUpdateResponse(product.getId());
	}

	@Transactional
	public void delete(Long productId) {
		Product product = findProductEntity(productId);
		productOptionRepository.deleteAllByProductId(product.getId());
		productRepository.delete(product);
	}

	@Transactional
	public void compareHighestPrice(Long productOptionId, int newPrice) {
		ProductOption productOption = findProductOptionEntity(productOptionId);
		int highestPrice = productOption.getHighestPrice();
		if (highestPrice < newPrice) {
			updateHighestPrice(productOption, newPrice);
		}
	}

	@Transactional
	public void compareLowestPrice(Long productOptionId, int newPrice) {
		ProductOption productOption = findProductOptionEntity(productOptionId);
		int lowestPrice = productOption.getLowestPrice();
		if (lowestPrice == 0 || lowestPrice > newPrice) {
			updateLowestPrice(productOption, newPrice);
		}
	}

	public void changeHighestPrice(Long productOptionId, int newPrice) {
		ProductOption productOption = findProductOptionEntity(productOptionId);
		updateHighestPrice(productOption, newPrice);
	}

	public void changLowestPrice(Long productOptionId, int newPrice) {
		ProductOption productOption = findProductOptionEntity(productOptionId);
		updateLowestPrice(productOption, newPrice);
	}

	@CacheEvict(value = "product", key = "#productOption.product.id")
	public void updateHighestPrice(ProductOption productOption, int newPrice) {
		productOption.updateHighestPrice(newPrice);
	}

	@CacheEvict(value = "product", key = "#productOption.product.id")
	public void updateLowestPrice(ProductOption productOption, int newPrice) {
		productOption.updateLowestPrice(newPrice);
	}

	private Product findProductEntity(Long productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException("productId does not exist"));
	}

	private ProductOption findProductOptionEntity(Long productOptionId) {
		return productOptionRepository.findById(productOptionId)
				.orElseThrow(() -> new EntityNotFoundException("productOptionId does not exist"));
	}
}
