package com.prgrms.kream.domain.product.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.kream.MysqlTestContainer;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterResponse;
import com.prgrms.kream.domain.product.facade.dto.ProductRegisterDto;
import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest extends MysqlTestContainer {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Nested
	@DisplayName("상품 등록 테스트")
	class Register {

		@Test
		@DisplayName("상품을 등록한다")
		void success() {
			//given
			ProductRegisterDto productRegisterDto
					= new ProductRegisterDto("나이키 데이브레이크", 100000, "23년 출시");

			Product product = Product.builder()
					.id(1L)
					.name("나이키 데이브레이크")
					.releasePrice(100000)
					.description("2023년 출시")
					.build();

			//mocking
			given(productRepository.save(any(Product.class)))
					.willReturn(product);

			//when
			ProductRegisterResponse result = productService.register(productRegisterDto);

			//then
			assertThat(result).usingRecursiveComparison().isEqualTo(product);
		}
	}
}
