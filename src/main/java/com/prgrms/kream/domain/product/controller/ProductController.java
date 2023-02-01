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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

	private final ProductFacade productFacade;

	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse<ProductGetResponse> getProduct(@PathVariable Long id) {
		return ApiResponse.of(productFacade.getProduct(id));
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse<ProductGetAllResponses> getAllProducts(
			@RequestBody @Valid ProductGetAllRequest productGetAllRequest) {
		return ApiResponse.of(productFacade.getAllProducts(productGetAllRequest));
	}
}
