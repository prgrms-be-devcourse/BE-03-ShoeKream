package com.prgrms.kream.domain.member.controller;

import static com.prgrms.kream.common.mapper.MemberMapper.*;
import static org.springframework.http.HttpStatus.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoDeleteRequest;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoUpdateRequest;
import com.prgrms.kream.domain.member.dto.request.FollowingDeleteRequest;
import com.prgrms.kream.domain.member.dto.request.FollowingRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.MemberLoginRequest;
import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.MemberUpdateRequest;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoGetResponse;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoRegisterResponse;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoUpdateResponse;
import com.prgrms.kream.domain.member.dto.response.FollowingGetAllResponse;
import com.prgrms.kream.domain.member.dto.response.MemberGetResponse;
import com.prgrms.kream.domain.member.dto.response.MemberRegisterResponse;
import com.prgrms.kream.domain.member.dto.response.MemberUpdateResponse;
import com.prgrms.kream.domain.member.facade.MemberFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberFacade memberFacade;

	@Value("${jwt.accessToken}")
	private String accessToken;

	@PostMapping("/signup")
	@ResponseStatus(CREATED)
	public ApiResponse<MemberRegisterResponse> registerMember(
			@RequestBody @Valid MemberRegisterRequest memberRegisterRequest
	) {
		return ApiResponse.of(memberFacade.registerMember(memberRegisterRequest));
	}

	@PostMapping("/login")
	@ResponseStatus(OK)
	public ApiResponse<String> loginMember(
			@RequestBody @Valid MemberLoginRequest memberLoginRequest,
			HttpServletResponse httpServletResponse
	) {
		httpServletResponse.addCookie(
				new Cookie(accessToken, memberFacade.loginMember(memberLoginRequest).token())
		);
		return ApiResponse.of("로그인 성공하였습니다.");
	}

	@GetMapping("/logout")
	@ResponseStatus(OK)
	public ApiResponse<String> logoutMember(HttpServletResponse httpServletResponse) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
			Cookie cookie = new Cookie(accessToken, "");
			cookie.setMaxAge(0);
			httpServletResponse.addCookie(cookie);
		}

		return ApiResponse.of("로그아웃 성공하였습니다.");
	}

	@GetMapping("/{memberId}")
	@ResponseStatus(OK)
	public ApiResponse<MemberGetResponse> getMember(@PathVariable("memberId") Long id) {
		return ApiResponse.of(memberFacade.getMember(id));
	}

	@PostMapping("/{memberId}")
	@ResponseStatus(OK)
	public ApiResponse<MemberUpdateResponse> updateMember(
			@PathVariable("memberId") Long id,
			@ModelAttribute @Valid MemberUpdateRequest memberUpdateRequest
	) {
		return ApiResponse.of(
				memberFacade.updateMember(toMemberUpdateFacadeRequest(id, memberUpdateRequest))
		);
	}

	@GetMapping("/{memberId}/addresses")
	@ResponseStatus(OK)
	public ApiResponse<Page<DeliveryInfoGetResponse>> getDeliveryInfoPage(
			@PathVariable("memberId") Long memberId, Pageable pageable
	) {
		return ApiResponse.of(
				memberFacade.getDeliveryInfoPage(memberId, pageable)
		);
	}

	@PostMapping("/{memberId}/addresses")
	@ResponseStatus(OK)
	public ApiResponse<DeliveryInfoRegisterResponse> registerDeliveryInfo(
			@RequestBody @Valid DeliveryInfoRegisterRequest deliveryInfoRegisterRequest
	) {
		return ApiResponse.of(memberFacade.registerDeliveryInfo(deliveryInfoRegisterRequest));
	}

	@PutMapping("/{memberId}/addresses")
	@ResponseStatus(OK)
	public ApiResponse<DeliveryInfoUpdateResponse> updateDeliveryInfo(
			@RequestBody @Valid DeliveryInfoUpdateRequest deliveryInfoUpdateRequest
	) {
		return ApiResponse.of(memberFacade.updateDeliveryInfo(deliveryInfoUpdateRequest));
	}

	@DeleteMapping("/{memberId}/addresses")
	@ResponseStatus(OK)
	public ApiResponse<String> deleteDeliveryInfo(
			@RequestBody @Valid DeliveryInfoDeleteRequest deliveryInfoDeleteRequest
	) {
		memberFacade.deleteDeliveryInfo(deliveryInfoDeleteRequest);
		return ApiResponse.of("삭제 성공하였습니다.");
	}

	@PostMapping("/{memberId}/followings")
	@ResponseStatus(CREATED)
	public ApiResponse<String> registerFollowing(
			@RequestBody @Valid FollowingRegisterRequest followingRegisterRequest
	) {
		memberFacade.registerFollowing(followingRegisterRequest);
		return ApiResponse.of("follow 등록에 성공했습니다.");
	}

	@DeleteMapping("/{memberId}/followings")
	@ResponseStatus(OK)
	public ApiResponse<String> deleteFollowing(
			@RequestBody @Valid FollowingDeleteRequest followingDeleteRequest
	) {
		memberFacade.deleteFollowing(followingDeleteRequest);
		return ApiResponse.of("follow 삭제에 성공했습니다.");
	}

	@GetMapping("/{memberId}/followings")
	@ResponseStatus(OK)
	public ApiResponse<FollowingGetAllResponse> getAllFollowings() {
		return ApiResponse.of(memberFacade.getAllFollowings());
	}
}
