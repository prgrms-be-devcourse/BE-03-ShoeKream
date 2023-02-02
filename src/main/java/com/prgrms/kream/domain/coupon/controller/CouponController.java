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

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/coupons")
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
	public ApiResponse<CouponResponse> registerCoupon(
			@RequestBody @Valid CouponRegisterRequest couponRegisterRequest
	) {
		CouponResponse couponResponse = couponService.registerCoupon(couponRegisterRequest);

		return ApiResponse.of(couponResponse);
	}


}
