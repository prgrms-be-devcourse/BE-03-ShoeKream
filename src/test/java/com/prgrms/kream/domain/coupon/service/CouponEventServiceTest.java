package com.prgrms.kream.domain.coupon.service;

import static com.prgrms.kream.common.mapper.CouponMapper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.dao.DuplicateKeyException;

import com.prgrms.kream.domain.coupon.dto.response.CouponEventResponse;
import com.prgrms.kream.domain.coupon.dto.request.CouponEventServiceRequest;
import com.prgrms.kream.domain.coupon.dto.response.CouponResponse;
import com.prgrms.kream.domain.coupon.model.Coupon;
import com.prgrms.kream.domain.coupon.model.CouponEvent;
import com.prgrms.kream.domain.coupon.repository.CouponEventRepository;
import com.prgrms.kream.domain.member.model.Member;

@AutoConfigureMockMvc
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
				.email("email")
				.name("name")
				.build();
		CouponEventServiceRequest couponEventServiceRequest = new CouponEventServiceRequest(member, couponResponse);
		CouponEvent couponEvent = CouponEvent.builder()
				.id(1L)
				.coupon(coupon)
				.member(couponEventServiceRequest.member())
				.build();

		//when
		when(couponEventRepository.save(any(CouponEvent.class))).thenReturn(couponEvent);
		CouponEventResponse couponEventControllerResponse = couponEventService.registerCouponEvent(couponEventServiceRequest);

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
				.email("email")
				.name("name")
				.build();
		CouponEventServiceRequest couponEventServiceRequest = new CouponEventServiceRequest(member, couponResponse);
		CouponEvent couponEvent = CouponEvent.builder()
				.id(1L)
				.coupon(coupon)
				.member(couponEventServiceRequest.member())
				.build();

		//when
		when(couponEventRepository.findByMember(any(Member.class))).thenReturn(Optional.ofNullable(couponEvent));


		//then
		assertThatThrownBy(
				() -> couponEventService.registerCouponEvent(couponEventServiceRequest)
				).isInstanceOf(DuplicateKeyException.class)
				.hasMessage("쿠폰을 중복으로 받을 수 없습니다.");
	}

}