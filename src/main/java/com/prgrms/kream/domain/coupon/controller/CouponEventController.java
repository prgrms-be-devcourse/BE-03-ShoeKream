package com.prgrms.kream.domain.coupon.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.coupon.dto.CouponEventControllerResponse;
import com.prgrms.kream.domain.coupon.dto.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.facade.CouponFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupon")
public class CouponEventController {
	private final CouponFacade couponFacade;

	/**
	 * 멤버에게 쿠폰을 발급한다.
	 * @author goseungwon
	 * @throw MethodArgumentNotValidException
	 * 					couponEventRegisterRequest.coupon_id 가 blank 인 경우,
	 * 					couponEventRegisterRequest.member_id 가 blank 인 경우
	 * @param couponEventRegisterRequest
	 * @return CouponEventControllerResponse
	 * @see CouponFacade
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<CouponEventControllerResponse> applyCouponEvent(
			@RequestBody @Valid CouponEventRegisterRequest couponEventRegisterRequest
	) {
		CouponEventControllerResponse couponEventControllerResponse =
				couponFacade.applyCouponEvent(couponEventRegisterRequest);

		return ApiResponse.of(couponEventControllerResponse);
	}

	/**
	 * 쿠폰 이벤트 참여 전 정적 페이지를 제공한다.
	 * @author goseungwon
	 * @return ModelAndView
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView showCouponEventPage() {
		return new ModelAndView("static_coupon_event_page");
	}
}
