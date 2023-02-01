package com.prgrms.kream.domain.member.service;

import static com.prgrms.kream.domain.member.model.Authority.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prgrms.kream.common.jwt.Jwt;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoDeleteRequest;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoUpdateRequest;
import com.prgrms.kream.domain.member.dto.request.FollowingRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.MemberLoginRequest;
import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.MemberUpdateServiceRequest;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoGetResponse;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoRegisterResponse;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoUpdateResponse;
import com.prgrms.kream.domain.member.dto.response.MemberGetFacadeResponse;
import com.prgrms.kream.domain.member.dto.response.MemberLoginResponse;
import com.prgrms.kream.domain.member.dto.response.MemberRegisterResponse;
import com.prgrms.kream.domain.member.dto.response.MemberUpdateServiceResponse;
import com.prgrms.kream.domain.member.model.DeliveryInfo;
import com.prgrms.kream.domain.member.model.Following;
import com.prgrms.kream.domain.member.model.FollowingId;
import com.prgrms.kream.domain.member.model.Member;
import com.prgrms.kream.domain.member.repository.DeliveryInfoRepository;
import com.prgrms.kream.domain.member.repository.FollowingRepository;
import com.prgrms.kream.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private Jwt jwt;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private DeliveryInfoRepository deliveryInfoRepository;

	@Mock
	private FollowingRepository followRepository;

	@BeforeEach
	void setUp() {
		SecurityContext context = SecurityContextHolder.getContext();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
				new UsernamePasswordAuthenticationToken(
						1L, null, List.of(new SimpleGrantedAuthority(ROLE_USER.name()))
				);

		context.setAuthentication(usernamePasswordAuthenticationToken);
	}

	@Test
	@DisplayName("회원가입 성공")
	void register_success() {
		Member member = Member.builder()
				.id(1L)
				.name("name")
				.email("email@naver.com")
				.password("aA!12345678")
				.phone("01012345678")
				.isMale(true)
				.authority(ROLE_USER)
				.build();

		MemberRegisterRequest memberRegisterRequest
				= new MemberRegisterRequest(
				"name", "email@naver.com", "01012345678", "aA12345678!", true, ROLE_USER
		);

		when(memberRepository.save(any(Member.class)))
				.thenReturn(member);

		MemberRegisterResponse memberRegisterResponse = memberService.register(memberRegisterRequest);

		// 테스트 추가
		Assertions.assertThat(memberRegisterResponse)
				.usingRecursiveComparison()
				.isEqualTo(member);

		verify(memberRepository, times(1)).save(any(Member.class));
	}

	@Test
	@DisplayName("로그인 실패 - 비밀번호 불일치함")
	void login_fail_password() {
		String password = "Pa!12345678";
		Member member = Member.builder()
				.id(1L)
				.email("hello@naver.com")
				.password(password)
				.name("hello")
				.phone("01012345678")
				.authority(ROLE_USER)
				.isMale(true)
				.build();

		when(memberRepository.findByEmail(member.getEmail()))
				.thenReturn(Optional.of(member));

		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(member.getEmail(), "wrongPassword");
		Assertions.assertThatThrownBy(() -> memberService.login(memberLoginRequest))
				.isInstanceOf(BadCredentialsException.class);

		verify(memberRepository, times(1)).findByEmail(member.getEmail());
		verify(jwt, times(0)).sign(any(Jwt.Claims.class));
	}

	@Test
	@DisplayName("로그인 실패 - 존재하지 않는 이메일")
	void login_fail_notExistEmail() {
		MemberLoginRequest memberLoginRequest = new MemberLoginRequest("wrongEmail", "Pa!12345678");
		Assertions.assertThatThrownBy(() -> memberService.login(memberLoginRequest))
				.isInstanceOf(EntityNotFoundException.class);

		verify(memberRepository, times(1)).findByEmail("wrongEmail");
		verify(jwt, times(0)).sign(any(Jwt.Claims.class));
	}

	@Test
	@DisplayName("로그인 성공")
	void login_fail() {
		String password = "Pa!12345678";
		Member member = Member.builder()
				.id(1L)
				.email("hello@naver.com")
				.password(password)
				.name("hello")
				.phone("01012345678")
				.authority(ROLE_USER)
				.isMale(true)
				.build();

		when(memberRepository.findByEmail(member.getEmail()))
				.thenReturn(Optional.of(member));

		String accessToken = "access_token";
		when(jwt.sign(any(Jwt.Claims.class))).thenReturn(accessToken);

		MemberLoginRequest memberLoginRequest = new MemberLoginRequest(member.getEmail(), password);
		MemberLoginResponse login = memberService.login(memberLoginRequest);

		Assertions.assertThat(login.token()).isEqualTo(accessToken);
		verify(memberRepository, times(1)).findByEmail(member.getEmail());
		verify(jwt, times(1)).sign(any(Jwt.Claims.class));
	}

	@Test
	@DisplayName("회원 정보 조회 성공")
	void get_success() {
		String password = "Pa!12345678";
		Member member = Member.builder()
				.id(1L)
				.email("hello@naver.com")
				.password(password)
				.name("hello")
				.phone("01012345678")
				.authority(ROLE_USER)
				.isMale(true)
				.build();

		when(memberRepository.findById(1L))
				.thenReturn(Optional.of(member));

		MemberGetFacadeResponse memberGetFacadeResponse = memberService.get(member.getId());

		Assertions.assertThat(memberGetFacadeResponse)
				.usingRecursiveComparison()
				.isEqualTo(member);

		verify(memberRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("회원 정보 조회 실패 - id에 해당하는 member 존재하지 않음")
	void get_fail_notExistMember() {
		when(memberRepository.findById(1L))
				.thenReturn(Optional.empty());

		Assertions.assertThatThrownBy(() -> memberService.get(1L))
				.isInstanceOf(EntityNotFoundException.class)
				.hasMessage("존재하지 않은 회원입니다.");

		verify(memberRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("회원 정보 조회 실패 - 사용자가 다른 사용자의 정보 조회")
	void get_fail_otherMember() {
		Assertions.assertThatThrownBy(() -> memberService.get(2L))
				.isInstanceOf(AccessDeniedException.class);

		verify(memberRepository, times(0)).findById(2L);
	}

	@Test
	@DisplayName("회원 정보 수정 성공")
	void update_success() {

		MemberUpdateServiceRequest memberUpdateServiceRequest
				= new MemberUpdateServiceRequest(
				1L,
				"updatedName",
				"01023456789",
				"changed!123"
		);

		Member member = Member.builder()
				.id(1L)
				.name("name")
				.email("hello@naver.com")
				.password("Pa!12345678")
				.phone("01012345678")
				.authority(ROLE_USER)
				.isMale(false)
				.build();

		when(memberRepository.findById(1L))
				.thenReturn(Optional.of(member));

		MemberUpdateServiceResponse memberUpdateServiceResponse
				= memberService.updateMember(memberUpdateServiceRequest);

		Assertions.assertThat(memberUpdateServiceResponse)
				.hasFieldOrPropertyWithValue("id", memberUpdateServiceRequest.id())
				.hasFieldOrPropertyWithValue("name", memberUpdateServiceRequest.name())
				.hasFieldOrPropertyWithValue("phone", memberUpdateServiceRequest.phone());

		verify(memberRepository, times(1)).findById(1L);

	}

	@Test
	@DisplayName("회원 정보 실패 - 다른 사용자의 정보를 조회")
	void update_fail_notValidMember() {
		MemberUpdateServiceRequest memberUpdateServiceRequest
				= new MemberUpdateServiceRequest(
				2L,
				"updatedName",
				"01023456789",
				"changed!123"
		);

		Assertions.assertThatThrownBy(
				() -> memberService.updateMember(memberUpdateServiceRequest)
		).isInstanceOf(AccessDeniedException.class);

		verify(memberRepository, times(0)).findById(anyLong());
	}

	@Test
	@DisplayName("배송 정보 조회 성공")
	void getDeliveryInfoPage_success() {
		DeliveryInfo deliveryInfo1 = DeliveryInfo.builder()
				.id(1L)
				.name("name1")
				.address("대한민국 서울 강남구~")
				.detail("101호")
				.phone("01012345678")
				.postCode("12345")
				.memberId(1L)
				.build();

		DeliveryInfo deliveryInfo2 = DeliveryInfo.builder()
				.id(2L)
				.name("name2")
				.address("대한민국 서울 도봉구~")
				.detail("102호")
				.phone("01023456789")
				.postCode("23456")
				.memberId(1L)
				.build();

		DeliveryInfo deliveryInfo3 = DeliveryInfo.builder()
				.id(3L)
				.name("name3")
				.address("대한민국 서울 노원구~")
				.detail("103호")
				.phone("01034567890")
				.postCode("34567")
				.memberId(1L)
				.build();

		DeliveryInfo deliveryInfo4 = DeliveryInfo.builder()
				.id(4L)
				.name("name4")
				.address("대한민국 서울 중랑구~")
				.detail("104")
				.phone("01045678901")
				.postCode("45678")
				.memberId(1L)
				.build();

		DeliveryInfo deliveryInfo5 = DeliveryInfo.builder()
				.id(5L)
				.name("name5")
				.address("대한민국 서울 종로구~")
				.detail("105")
				.phone("01056789012")
				.postCode("56789")
				.memberId(1L)
				.build();

		DeliveryInfo deliveryInfo6 = DeliveryInfo.builder()
				.id(6L)
				.name("name6")
				.address("대한민국 서울 영등포구~")
				.detail("서울")
				.phone("01067890123")
				.postCode("67890")
				.memberId(1L)
				.build();

		List<DeliveryInfo> deliveryInfoList = List.of(
				deliveryInfo1, deliveryInfo2, deliveryInfo3, deliveryInfo4, deliveryInfo5, deliveryInfo6
		);

		PageImpl<DeliveryInfo> result = new PageImpl<>(
				deliveryInfoList, PageRequest.of(0, 5), 6);

		when(deliveryInfoRepository.findAllByMemberId(
				eq(1L),
				eq(PageRequest.of(0, 5)))
		).thenReturn(result);

		Page<DeliveryInfoGetResponse> deliveryInfoPage =
				memberService.getDeliveryInfoPage(1L, PageRequest.of(0, 5));

		Assertions.assertThat(result)
				.usingRecursiveComparison()
				.isEqualTo(deliveryInfoPage);

		verify(deliveryInfoRepository, times(1))
				.findAllByMemberId(eq(1L), eq(PageRequest.of(0, 5)));
	}

	@Test
	@DisplayName("배송 정보 조회 실패 - 다른 사용자 정보 조회")
	void getDeliveryInfoPage_fail_notValidMember() {
		Assertions.assertThatThrownBy(() ->
						memberService.getDeliveryInfoPage(2L, PageRequest.of(0, 5))
				)
				.isInstanceOf(AccessDeniedException.class);

		verify(deliveryInfoRepository, times(0))
				.findAllByMemberId(eq(2L), eq(PageRequest.of(0, 5)));
	}

	@Test
	@DisplayName("배송 정보 저장 성공")
	void registerDeliveryInfo_success() {

		String name = "name1";
		String phone = "01012345678";
		String postCode = "12345";
		String address = "서울시 성동구~";
		String detail = "101호";
		Long memberId = 1L;

		DeliveryInfoRegisterRequest deliveryInfoRegisterRequest =
				new DeliveryInfoRegisterRequest(
						name,
						phone,
						postCode,
						address,
						detail,
						memberId
				);

		DeliveryInfo deliveryInfo = DeliveryInfo.builder()
				.id(1L)
				.name(name)
				.phone(phone)
				.address(address)
				.postCode(postCode)
				.detail(detail)
				.memberId(memberId)
				.build();

		when(deliveryInfoRepository.save(any(DeliveryInfo.class)))
				.thenReturn(deliveryInfo);

		DeliveryInfoRegisterResponse deliveryInfoRegisterResponse
				= memberService.registerDeliveryInfo(deliveryInfoRegisterRequest);

		Assertions.assertThat(deliveryInfoRegisterResponse.deliveryInfoId())
				.isEqualTo(1L);

		verify(deliveryInfoRepository, times(1)).save(any(DeliveryInfo.class));

	}

	@Test
	@DisplayName("배송 정보 저장 실패 - 로그인한 사용자와 다른 사용자에 대한 정보 추가")
	void registerDeliveryInfo_fail_NotValidMember() {
		String name = "name1";
		String phone = "01012345678";
		String postCode = "12345";
		String address = "서울시 성동구~";
		String detail = "101호";
		Long memberId = 2L;

		DeliveryInfoRegisterRequest deliveryInfoRegisterRequest =
				new DeliveryInfoRegisterRequest(
						name,
						phone,
						postCode,
						address,
						detail,
						memberId
				);

		Assertions.assertThatThrownBy(() ->
						memberService.registerDeliveryInfo(deliveryInfoRegisterRequest)
				)
				.isInstanceOf(AccessDeniedException.class);

		verify(deliveryInfoRepository, times(0)).save(any(DeliveryInfo.class));
	}

	@Test
	@DisplayName("배송 정보 수정 성공")
	void updateDeliveryInfo_success() {
		Long deliveryId = 1L;
		Long memberId = 1L;

		DeliveryInfo deliveryInfo = DeliveryInfo.builder()
				.id(deliveryId)
				.name("name1")
				.phone("01012345678")
				.detail("101호")
				.address("서울시 성동구~")
				.postCode("12345")
				.memberId(memberId)
				.build();

		DeliveryInfoUpdateRequest deliveryInfoUpdateRequest =
				new DeliveryInfoUpdateRequest(
						deliveryId,
						"changedName",
						"01023456789",
						"23456",
						"서울시 강동구~",
						"201호",
						memberId
				);

		when(deliveryInfoRepository.findById(deliveryId))
				.thenReturn(Optional.of(deliveryInfo));

		DeliveryInfoUpdateResponse deliveryInfoUpdateResponse
				= memberService.updateDeliveryInfo(deliveryInfoUpdateRequest);

		Assertions.assertThat(deliveryInfoUpdateResponse)
				.usingRecursiveComparison()
				.isEqualTo(deliveryInfoUpdateRequest);

		verify(deliveryInfoRepository, times(1)).findById(deliveryId);
	}

	@Test
	@DisplayName("배송 정보 수정 실패 - 로그인한 사용자와 다른 사용자에 대한 정보 수정")
	void updateDeliveryInfo_fail_NotValidMember() {
		Long deliveryId = 1L;
		Long memberId = 2L;

		DeliveryInfoUpdateRequest deliveryInfoUpdateRequest =
				new DeliveryInfoUpdateRequest(
						deliveryId,
						"changedName",
						"01023456789",
						"23456",
						"서울시 강동구~",
						"201호",
						memberId
				);

		Assertions.assertThatThrownBy(
				() -> memberService.updateDeliveryInfo(deliveryInfoUpdateRequest)
		).isInstanceOf(AccessDeniedException.class);

		verify(deliveryInfoRepository, times(0)).findById(deliveryId);
	}

	@Test
	@DisplayName("배송 정보 삭제 성공")
	void deleteDeliveryInfo_success() {
		memberService.deleteDeliveryInfo(new DeliveryInfoDeleteRequest(1L));
		verify(deliveryInfoRepository, times(1)).deleteById(1L);
	}

	@Test
	@DisplayName("팔로우 등록 성공 - 이미 등록된 경우")
	void registerFollow_success_alreadyExistFollow() {

		when(memberRepository.existsById(2L))
				.thenReturn(false);

		memberService.registerFollowing(new FollowingRegisterRequest(2L));

		verify(memberRepository, times(1)).existsById(2L);
		verify(followRepository, times(0)).existsById(any(FollowingId.class));
		verify(followRepository, times(0)).save(any(Following.class));

	}

	@Test
	@DisplayName("팔로우 등록 성공 - 아직 등록되지 않은 경우")
	void registerFollow_success_notYetExistFollow() {

		FollowingId followId = new FollowingId(1L, 2L);
		Following follow = new Following(followId);

		when(memberRepository.existsById(2L))
				.thenReturn(true);
		when(followRepository.existsById(any(FollowingId.class)))
				.thenReturn(false);
		when(followRepository.save(any(Following.class)))
				.thenReturn(follow);

		memberService.registerFollowing(new FollowingRegisterRequest(2L));

		verify(memberRepository, times(1)).existsById(2L);
		verify(followRepository, times(1)).existsById(any(FollowingId.class));
		verify(followRepository, times(1)).save(any(Following.class));

	}
}
