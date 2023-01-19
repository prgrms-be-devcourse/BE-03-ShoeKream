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
import com.prgrms.kream.domain.coupon.dto.CouponEventDto;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
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
	 * @param couponEventRegisterRequest 쿠폰 id, 멤버 id
	 * @return CouponEventControllerResponse
	 * @see CouponFacade
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<CouponEventDto> applyCouponEvent(
			@RequestBody @Valid CouponEventRegisterRequest couponEventRegisterRequest
	) {
		CouponEventDto couponEventControllerResponse =
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
