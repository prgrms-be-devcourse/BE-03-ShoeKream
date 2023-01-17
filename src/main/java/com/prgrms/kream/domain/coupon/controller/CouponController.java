package com.prgrms.kream.domain.coupon.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.coupon.dto.CouponControllerResponse;
import com.prgrms.kream.domain.coupon.dto.CouponRegisterRequest;
import com.prgrms.kream.domain.coupon.service.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/coupon")
public class CouponController {
	private final CouponService couponService;

	/**
	 * 관리자가 쿠폰을 생성한다.
	 * @author goseungwon
	 * @throws  MethodArgumentNotValidException
	 * 					couponRegisterRequest.discountValue 가 0 이상 100 이하가 아닌 경우 or null 인 경우,
	 * 					couponRegisterRequest.name 의 길이가 20 이상인 경우 or blank 인 경우,
	 * 					couponRegisterRequest.amount 가 0 이상이 아닌 경우 or null 인 경우,
	 * @param couponRegisterRequest
	 * @return CouponControllerResponse
	 * @see CouponService
	 */
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponse<CouponControllerResponse> couponRegister(
			@RequestBody @Valid CouponRegisterRequest couponRegisterRequest
	) {
		CouponControllerResponse couponControllerResponse = couponService.registerCoupon(couponRegisterRequest);

		return ApiResponse.of(couponControllerResponse);
	}


}
