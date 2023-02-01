package com.prgrms.kream.domain.product.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

@ExtendWith(MockitoExtension.class)
@DisplayName("Service layer를 통해 ")
public class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductOptionRepository productOptionRepository;

	@Test
	@DisplayName("상품을 등록한다")
	void register() {
		//given
		List<Integer> sizes = List.of(200, 210);

		ProductRegisterFacadeRequest productRegisterFacadeRequest
				= new ProductRegisterFacadeRequest("Nike Zoom Vomero 5 SP", 189000, "Anthracite 2023", sizes);

		Product product = Product.builder()
				.id(1L)
				.name("Nike Zoom Vomero 5 SP")
				.releasePrice(189000)
				.description("Anthracite 2023")
				.build();

		//mocking
		when(productRepository.save(any(Product.class)))
				.thenReturn(product);

		//when
		ProductRegisterResponse result = productService.registerProduct(productRegisterFacadeRequest);

		//then
		assertThat(result).usingRecursiveComparison().isEqualTo(product);
	}

	@Test
	@DisplayName("상품 상세 정보를 조회한다.")
	void get() {
		//given
		Long productId = 1L;

		Product product = Product.builder()
				.id(1L)
				.name("Nike Dunk Low")
				.releasePrice(129000)
				.description("Black")
				.build();

		//mocking
		when(productRepository.findById(productId))
				.thenReturn(Optional.of(product));

		//when
		ProductGetFacadeResponse result = productService.getProduct(productId);

		//then
		assertThat(result).usingRecursiveComparison().isEqualTo(product);
	}

	@Test
	@DisplayName("존재하지 않는 상품 id로 조회하면 예외가 발생한다")
	void get_with_not_exist_id() {
		assertThatThrownBy(() -> productService.getProduct(40L))
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("productId does not exist");
	}

	@Test
	@DisplayName("상품 목록을 조회한다")
	void getAll() {
		//given
		Long cursorId = 5L;
		int pageSize = 2;
		String searchWord = "";
		ProductGetAllRequest productGetAllRequest = new ProductGetAllRequest(cursorId, pageSize, searchWord);

		List<Product> products = List.of(
				Product.builder()
						.id(4L)
						.name("Nike Dunk Low")
						.releasePrice(129000)
						.description("Retro Black")
						.build(),
				Product.builder()
						.id(3L)
						.name("Nike Air Force 1 '07")
						.releasePrice(169000)
						.description("WB Flax")
						.build()
		);

		//mocking
		when(productRepository.findAllByCursor(cursorId, pageSize, searchWord))
				.thenReturn(products);

		//when
		ProductGetAllResponses productGetAllResponses = productService.getAllProducts(productGetAllRequest);

		//then
		assertThat(productGetAllResponses.productGetAllResponses()).hasSize(2);
		assertThat(productGetAllResponses.productGetAllResponses()).usingRecursiveComparison().isEqualTo(products);
	}

	@Test
	@DisplayName("상품을 수정한다")
	void update() {
		//given
		Long productId = 1L;
		List<Integer> sizes = List.of(200, 210);
		ProductUpdateFacadeRequest productUpdateFacadeRequest
				= new ProductUpdateFacadeRequest(1L, 189999, "Vast Grey 2023", sizes);

		Product product = Product.builder()
				.id(1L)
				.name("Nike Zoom Vomero 5 SP")
				.releasePrice(189000)
				.description("Anthracite 2023")
				.build();

		//mocking
		when(productRepository.findById(productId))
				.thenReturn(Optional.of(product));

		//when
		ProductUpdateResponse productUpdateResponse = productService.update(productUpdateFacadeRequest);

		//then
		assertThat(productUpdateResponse.id()).isEqualTo(productId);
	}

	@Test
	@DisplayName("상품을 삭제한다")
	void delete() {
		//given
		Long productId = 1L;

		Product product = Product.builder()
				.id(1L)
				.name("Nike Dunk Low")
				.releasePrice(129000)
				.description("Black")
				.build();

		//mocking
		when(productRepository.findById(productId))
				.thenReturn(Optional.of(product));
		doNothing()
				.when(productRepository).delete(any(Product.class));

		//when
		productService.delete(productId);

		//then
		verify(productRepository, times(1)).delete(product);
		verify(productOptionRepository, times(1)).deleteAllByProductId(product.getId());
	}

	@Test
	@DisplayName("새로운 입찰가가 더 높을 시, 상품의 최고가로 등록한다")
	void compareHighestPrice() {
		//given
		Product product = Product.builder()
				.id(1L)
				.name("Nike Dunk Low")
				.releasePrice(129000)
				.description("Black")
				.build();

		ProductOption productOption = ProductOption.builder()
				.id(1L)
				.size(220)
				.product(product)
				.build();

		//mocking
		when(productOptionRepository.findById(any()))
				.thenReturn(Optional.of(productOption));

		//when
		productService.compareHighestPrice(1L, 130000);

		//then
		verify(productOptionRepository, times(1)).findById(1L);
	}
}
