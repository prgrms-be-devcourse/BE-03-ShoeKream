package com.prgrms.kream.domain.coupon.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.kream.domain.coupon.dto.request.CouponRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponResponse;
import com.prgrms.kream.domain.coupon.model.Coupon;
import com.prgrms.kream.domain.coupon.repository.CouponRepository;

@ExtendWith(MockitoExtension.class)
public class CouponServiceTest {
	@Mock
	CouponRepository couponRepository;

	@InjectMocks
	CouponService couponService;

	CouponRegisterRequest couponRegisterRequest = new CouponRegisterRequest(100, "테스트 쿠폰", 10000);
	Coupon coupon = Coupon.builder()
			.id(1L)
			.amount(couponRegisterRequest.amount())
			.name(couponRegisterRequest.name())
			.discountValue(couponRegisterRequest.discountValue())
			.build();

	@Test
	@DisplayName("쿠폰 생성 테스트")
	void registerCouponTest() {
		//given

		//when
		when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);
		CouponResponse couponRegisterDto = couponService.registerCoupon(couponRegisterRequest);

		//then
		assertThat(couponRegisterDto.id()).isEqualTo(coupon.getId());
	}

	@Test
	@DisplayName("쿠폰 조회 테스트")
	void getCouponByIdTest() {
		//given
		when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));

		//when
		CouponResponse couponById = couponService.getCouponById(coupon.getId());

		//then
		assertThat(couponById.id()).isEqualTo(coupon.getId());
	}

	@Test
	@DisplayName("쿠폰 조회 예외 테스트")
	void getCouponByIdExceptionTest() {
		//given

		//when

		//then
		assertThatThrownBy(() ->
				couponService.getCouponById(1L)).isInstanceOf(EntityNotFoundException.class)
				.hasMessage("존재하지 않는 Coupon couponId: 1");
	}
}
