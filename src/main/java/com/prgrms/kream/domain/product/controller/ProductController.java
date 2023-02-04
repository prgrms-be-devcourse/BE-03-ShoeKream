package com.prgrms.kream.domain.product.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.product.dto.request.ProductGetAllRequest;
import com.prgrms.kream.domain.product.dto.response.ProductGetAllResponses;
import com.prgrms.kream.domain.product.dto.response.ProductGetResponse;
import com.prgrms.kream.domain.product.facade.ProductFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Api(tags = "상품 컨트롤러")
public class ProductController {

	private final ProductFacade productFacade;

	/**
	 * 사용자가 상품 상세 정보를 조회한다.
	 * @author kimtaehee
	 * @param id 상세 조회할 상품 아이디
	 * @return ProductGetResponse
	 * @see ProductFacade
	 */
	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "상품 상세 조회")
	public ApiResponse<ProductGetResponse> getProduct(
			@ApiParam(value = "상세 조회할 상품 아이디", required = true, example = "1") @PathVariable Long id) {
		return ApiResponse.of(productFacade.getProduct(id));
	}

	/**
	 * 사용자가 상품 목록을 조회한다.
	 * @author kimtaehee
	 * @param productGetAllRequest 상품 목록 조회 요청 정보(커서 아이디, 페이지 사이즈, 검색어)
	 * @return ProductGetAllResponses
	 * @see ProductFacade
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "상품 목록 조회")
	public ApiResponse<ProductGetAllResponses> getAllProducts(
			@ApiParam(value = "상품 목록 조회 요청 정보", required = true)
			@RequestBody @Valid ProductGetAllRequest productGetAllRequest) {
		return ApiResponse.of(productFacade.getAllProducts(productGetAllRequest));
	}
}
