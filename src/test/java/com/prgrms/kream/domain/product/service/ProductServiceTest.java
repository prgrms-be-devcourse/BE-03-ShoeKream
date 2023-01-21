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
import com.prgrms.kream.domain.product.dto.response.ProductGetAllResponses;
import com.prgrms.kream.domain.product.dto.response.ProductGetFacadeResponse;
import com.prgrms.kream.domain.product.dto.response.ProductRegisterResponse;
import com.prgrms.kream.domain.product.model.Product;
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
				= new ProductRegisterFacadeRequest("나이키 데이브레이크", 100000, "23년 출시", sizes);

		Product product = Product.builder()
				.id(1L)
				.name("나이키 데이브레이크")
				.releasePrice(100000)
				.description("23년 출시")
				.build();

		//mocking
		given(productRepository.save(any(Product.class)))
				.willReturn(product);

		//when
		ProductRegisterResponse result = productService.register(productRegisterFacadeRequest);

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
		given(productRepository.findById(productId))
				.willReturn(Optional.of(product));

		//when
		ProductGetFacadeResponse result = productService.get(productId);

		//then
		assertThat(result).usingRecursiveComparison().isEqualTo(product);
	}

	@Test
	@DisplayName("존재하지 않는 상품 id로 조회하면 예외가 발생한다")
	void get_with_not_exist_id() {
		assertThatThrownBy(() -> productService.get(40L))
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("productId does not exist");
	}

	@Test
	@DisplayName("상품 목록을 조회한다")
	void getAll() {
		//given
		Long cursorId = 5L;
		int pageSize = 2;
		ProductGetAllRequest productGetAllRequest = new ProductGetAllRequest(cursorId, pageSize);

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
		given(productRepository.findAllByCursor(cursorId, pageSize))
				.willReturn(products);

		//when
		ProductGetAllResponses productGetAllResponses = productService.getAll(productGetAllRequest);

		//then
		assertThat(productGetAllResponses.productGetAllResponses()).hasSize(2);
		assertThat(productGetAllResponses.productGetAllResponses()).usingRecursiveComparison().isEqualTo(products);
	}
}
