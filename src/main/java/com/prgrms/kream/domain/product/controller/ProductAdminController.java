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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
@Api(tags = "상품 관리자 컨트롤러")
public class ProductAdminController {

	private final ProductFacade productFacade;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "상품 등록")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "상품 이름", required = true),
			@ApiImplicitParam(name = "releasePrice", value = "상품 출시가격", required = true),
			@ApiImplicitParam(name = "description", value = "상품 설명", required = true),
			@ApiImplicitParam(name = "sizes", value = "상품 신발 사이즈", required = true),
			@ApiImplicitParam(name = "images", value = "상품 이미지", required = true)
	})
	public ApiResponse<ProductRegisterResponse> registerProduct(
			@ModelAttribute @Valid ProductRegisterRequest productRegisterRequest) {

		return ApiResponse.of(productFacade.registerProduct(productRegisterRequest));
	}

	@PatchMapping
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "상품 수정")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "수정할 상품 아이디", required = true),
			@ApiImplicitParam(name = "releasePrice", value = "상품 출시가격", required = true),
			@ApiImplicitParam(name = "description", value = "상품 설명", required = true),
			@ApiImplicitParam(name = "sizes", value = "상품 신발 사이즈", required = true),
			@ApiImplicitParam(name = "images", value = "상품 이미지", required = true)
	})
	public ApiResponse<ProductUpdateResponse> updateProduct(
			@ApiParam(value = "상품 수정 요청 정보", required = true)
			@ModelAttribute @Valid ProductUpdateRequest productUpdateRequest) {

		return ApiResponse.of(productFacade.updateProduct(productUpdateRequest));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "상품 삭제")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse<String> deleteProduct(
			@ApiParam(value = "삭제할 상품 아이디", required = true, example = "1") @PathVariable Long id) {
		productFacade.deleteProduct(id);

		return ApiResponse.of("삭제되었습니다.");
	}
}
