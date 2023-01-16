package com.prgrms.kream.domain.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.product.dto.ProductRegisterRequest;
import com.prgrms.kream.domain.product.dto.ProductRegisterResponse;
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
			@RequestPart ProductRegisterRequest productRegisterRequest,
			@RequestPart List<MultipartFile> multipartFiles) {

		ProductRegisterResponse productRegisterResponse
				= productFacade.register(productRegisterRequest, multipartFiles);

		return ApiResponse.of(productRegisterResponse);
	}
}