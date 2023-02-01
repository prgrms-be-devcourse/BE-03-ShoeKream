package com.prgrms.kream.domain.coupon.service;

import static com.prgrms.kream.common.mapper.CouponMapper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.kream.domain.coupon.dto.request.CouponEventRegisterRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
import com.prgrms.kream.domain.coupon.dto.response.CouponResponse;
import com.prgrms.kream.domain.coupon.model.Coupon;
import com.prgrms.kream.domain.coupon.model.CouponEvent;
import com.prgrms.kream.domain.coupon.repository.CouponEventRepository;
import com.prgrms.kream.domain.member.model.Member;
import com.sun.jdi.request.DuplicateRequestException;

@ExtendWith(MockitoExtension.class)
class CouponEventServiceTest {
	@Mock
	CouponEventRepository couponEventRepository;

	@InjectMocks
	CouponEventService couponEventService;

	@Test
	@DisplayName("쿠폰 발급 테스트")
	void registerCouponEventTest() {
		//given
		Coupon coupon = Coupon.builder()
				.id(1L)
				.amount(100)
				.name("쿠폰 이름")
				.discountValue(50)
				.build();
		CouponResponse couponResponse = toCouponResponse(coupon);
		Member member = Member.builder()
				.id(1L)
				.email("test@test.com")
				.phone("01012345678")
				.password("1234asdf!@#$")
				.isMale(true)
				// .authority("ADMIN")
				.name("name")
				.build();
		CouponEventRegisterRequest couponEventRegisterRequest =
				new CouponEventRegisterRequest(member.getId(), couponResponse.id());
		CouponEvent couponEvent = CouponEvent.builder()
				.id(1L)
				.couponId(coupon.getId())
				.memberId(couponEventRegisterRequest.memberId())
				.build();

		//when
		when(couponEventRepository.save(any(CouponEvent.class)))
				.thenReturn(couponEvent);
		CouponEventResponse couponEventControllerResponse = couponEventService.registerCouponEvent(couponEventRegisterRequest);

		//then
		assertThat(couponEventControllerResponse.id()).isEqualTo(couponEvent.getId());
	}

	@Test
	@DisplayName("중복 발급 테스트")
	void OverLapCouponEventTest() {
		//given
		Coupon coupon = Coupon.builder()
				.id(1L)
				.amount(100)
				.name("쿠폰 이름")
				.discountValue(50)
				.build();
		CouponResponse couponResponse = toCouponResponse(coupon);
		Member member = Member.builder()
				.id(1L)
				.email("test@test.com")
				.phone("01012345678")
				.password("1234asdf!@#$")
				.isMale(true)
				// .authority("ADMIN")
				.name("name")
				.build();
		CouponEventRegisterRequest couponEventRegisterRequest =
				new CouponEventRegisterRequest(member.getId(), couponResponse.id());

		//when
		when(couponEventRepository.existsByMemberIdAndCouponId(any(Long.class), any(Long.class)))
				.thenReturn(true);


		//then
		assertThatThrownBy(
				() -> couponEventService.registerCouponEvent(couponEventRegisterRequest)
				).isInstanceOf(DuplicateRequestException.class)
				.hasMessage("쿠폰을 중복으로 받을 수 없습니다.");
	}

}