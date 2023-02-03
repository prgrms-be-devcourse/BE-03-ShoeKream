package com.prgrms.kream.domain.coupon.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.coupon.dto.request.CouponRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponResponse;
import com.prgrms.kream.domain.coupon.service.CouponService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/coupons")
@Api(tags = "쿠폰 컨트롤러")
public class CouponController {
	private final CouponService couponService;

	/**
	 * 관리자가 쿠폰을 생성한다.
	 * @author goseungwon
	 * @param couponRegisterRequest 할인율, 쿠폰 이름, 쿠폰 수량
	 * @return CouponResponse
	 * @see CouponService
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "쿠폰 생성")
	public ApiResponse<CouponResponse> registerCoupon(
			@ApiParam(value = "생성할 쿠폰 요청 정보", required = true)
			@RequestBody @Valid CouponRegisterRequest couponRegisterRequest
	) {
		CouponResponse couponResponse = couponService.registerCoupon(couponRegisterRequest);

		return ApiResponse.of(couponResponse);
	}
}
