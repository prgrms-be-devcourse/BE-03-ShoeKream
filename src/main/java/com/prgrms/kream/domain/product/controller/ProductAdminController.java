package com.prgrms.kream.domain.product.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterRequest;
import com.prgrms.kream.domain.product.controller.dto.ProductRegisterResponse;

import com.prgrms.kream.domain.product.facade.ProductFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/product")
public class ProductAdminController {

	private final ProductFacade productFacade;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponse<ProductRegisterResponse> register(
			@ModelAttribute ProductRegisterRequest productRegisterRequest) {

		return ApiResponse.of(productFacade.register(productRegisterRequest));
	}
}
