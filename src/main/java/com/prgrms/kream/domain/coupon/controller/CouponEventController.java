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
import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.facade.CouponFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
@Api(tags = {"쿠폰 이벤트 컨트롤러"})
public class CouponEventController {
	private final CouponFacade couponFacade;

	/**
	 * 쿠폰 이벤트 요청을 레디스에 추가한다.
	 * @author goseungwon
	 * @param couponEventRegisterRequest 쿠폰 id, 멤버 id
	 * @return CouponEventResponse
	 * @see CouponFacade
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "쿠폰 발급")
	public ApiResponse<Long> applyCouponEvent(
			@ApiParam(value = "발급할 쿠폰과 사용자 요청 정보", required = true)
			@RequestBody @Valid CouponEventRegisterRequest couponEventRegisterRequest
	) {
		long queueSize = couponFacade.applyCountEvent(couponEventRegisterRequest);
		return ApiResponse.of(queueSize);
	}

	/**
	 * 쿠폰 이벤트 참여 전 정적 페이지를 제공한다.
	 * @author goseungwon
	 * @return ModelAndView
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "쿠폰 이벤트 페이지 요청")
	public ModelAndView showCouponEventPage() {
		return new ModelAndView("static_coupon_event_page");
	}
}
