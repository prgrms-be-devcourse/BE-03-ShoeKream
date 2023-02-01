package com.prgrms.kream.domain.product.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.product.dto.request.ProductRegisterRequest;
import com.prgrms.kream.domain.product.dto.request.ProductUpdateRequest;
import com.prgrms.kream.domain.product.dto.response.ProductRegisterResponse;
import com.prgrms.kream.domain.product.dto.response.ProductUpdateResponse;
import com.prgrms.kream.domain.product.facade.ProductFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class ProductAdminController {

	private final ProductFacade productFacade;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponse<ProductRegisterResponse> registerProduct(
			@ModelAttribute @Valid ProductRegisterRequest productRegisterRequest) {

		return ApiResponse.of(productFacade.registerProduct(productRegisterRequest));
	}

	@PatchMapping
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse<ProductUpdateResponse> update(
			@ModelAttribute @Valid ProductUpdateRequest productUpdateRequest) {

		return ApiResponse.of(productFacade.update(productUpdateRequest));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse<String> deleteProduct(@PathVariable Long id) {
		productFacade.deleteProduct(id);

		return ApiResponse.of("삭제되었습니다.");
	}
}
