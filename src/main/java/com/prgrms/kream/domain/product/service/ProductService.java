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
	public ProductRegisterResponse registerProduct(ProductRegisterFacadeRequest productRegisterFacadeRequest) {
		Product product = productRepository.save(toProduct(productRegisterFacadeRequest));

		productOptionRepository.saveAllBulk(
				toProductOptions(productRegisterFacadeRequest.sizes(), product)
		);

		return toProductRegisterResponse(product.getId());
	}

	@Transactional(readOnly = true)
	public ProductGetFacadeResponse getProduct(Long productId) {
		Product product = getProductEntity(productId);
		List<ProductOption> productOptions = productOptionRepository.findAllByProduct(product);
		return toProductGetFacadeResponse(product, productOptions);
	}

	@Transactional(readOnly = true)
	public ProductGetAllResponses getAllProducts(ProductGetAllRequest productGetAllRequest) {
		List<Product> products = productRepository.findAllByCursor(
				productGetAllRequest.cursorId(), productGetAllRequest.pageSize(), productGetAllRequest.searchWord());

		Long lastId = -1L;
		if (products.size() != 0) {
			lastId = products.get(products.size() - 1).getId();
		}

		return toProductGetAllResponses(products, lastId);
	}

	@Transactional
	public ProductUpdateResponse updateProduct(ProductUpdateFacadeRequest productFacadeUpdateRequest) {
		Product product = getProductEntity(productFacadeUpdateRequest.id());
		product.update(productFacadeUpdateRequest.releasePrice(), productFacadeUpdateRequest.description());

		productOptionRepository.deleteAllByProductId(product.getId());
		productOptionRepository.saveAllBulk(
				toProductOptions(productFacadeUpdateRequest.sizes(), product)
		);

		return toProductUpdateResponse(product.getId());
	}

	@Transactional
	public void deleteProduct(Long productId) {
		Product product = getProductEntity(productId);
		productOptionRepository.deleteAllByProductId(product.getId());
		productRepository.delete(product);
	}

	@Transactional
	public void compareHighestPrice(Long productOptionId, int newPrice) {
		ProductOption productOption = getProductOptionEntity(productOptionId);
		int highestPrice = productOption.getHighestPrice();
		if (highestPrice < newPrice) {
			changeHighestPrice(productOption, newPrice);
		}
	}

	@Transactional
	public void compareLowestPrice(Long productOptionId, int newPrice) {
		ProductOption productOption = getProductOptionEntity(productOptionId);
		int lowestPrice = productOption.getLowestPrice();
		if (lowestPrice == 0 || lowestPrice > newPrice) {
			changeLowestPrice(productOption, newPrice);
		}
	}

	public void updateHighestPrice(Long productOptionId, int newPrice) {
		ProductOption productOption = getProductOptionEntity(productOptionId);
		changeHighestPrice(productOption, newPrice);
	}

	public void updateLowestPrice(Long productOptionId, int newPrice) {
		ProductOption productOption = getProductOptionEntity(productOptionId);
		changeLowestPrice(productOption, newPrice);
	}

	@CacheEvict(value = "product", key = "#productOption.product.id")
	public void changeHighestPrice(ProductOption productOption, int newPrice) {
		productOption.updateHighestPrice(newPrice);
	}

	@CacheEvict(value = "product", key = "#productOption.product.id")
	public void changeLowestPrice(ProductOption productOption, int newPrice) {
		productOption.updateLowestPrice(newPrice);
	}

	private Product getProductEntity(Long productId) {
		return productRepository.findById(productId)
				.orElseThrow(() -> new EntityNotFoundException("productId does not exist"));
	}

	private ProductOption getProductOptionEntity(Long productOptionId) {
		return productOptionRepository.findById(productOptionId)
				.orElseThrow(() -> new EntityNotFoundException("productOptionId does not exist"));
	}
}
