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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Api(tags = "사용자 컨트롤러")
public class MemberController {

	private final MemberFacade memberFacade;

	@Value("${jwt.accessToken}")
	private String accessToken;

	/**
	 * 사용자가 회원가입을 한다.
	 * @author KimJuHyeong
	 * @return MemberRegisterResponse
	 * @see MemberFacade
	 */
	@PostMapping("/signup")
	@ResponseStatus(CREATED)
	@ApiOperation(value = "사용자 회원가입")
	public ApiResponse<MemberRegisterResponse> registerMember(
			@ApiParam(value = "회원가입 요청 정보", required = true)
			@RequestBody @Valid MemberRegisterRequest memberRegisterRequest
	) {
		return ApiResponse.of(memberFacade.registerMember(memberRegisterRequest));
	}

	/**
	 * 사용자가 로그인을 한다.
	 * @author KimJuHyeong
	 * @return String
	 * @see MemberFacade
	 */
	@PostMapping("/login")
	@ResponseStatus(OK)
	@ApiOperation(value = "사용자 로그인")
	public ApiResponse<String> loginMember(
			@ApiParam(value = "로그인 요청 정보", required = true)
			@RequestBody @Valid MemberLoginRequest memberLoginRequest,
			HttpServletResponse httpServletResponse
	) {
		String token = memberFacade.loginMember(memberLoginRequest).token();
		httpServletResponse.addCookie(
				new Cookie(accessToken, token)
		);
		return ApiResponse.of(token);
	}

	/**
	 * 사용자가 로그아웃을 한다.
	 * @author KimJuHyeong
	 * @return String
	 * @see MemberFacade
	 */
	@GetMapping("/logout")
	@ResponseStatus(OK)
	@ApiOperation(value = "사용자 로그아웃")
	public ApiResponse<String> logoutMember(HttpServletResponse httpServletResponse) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && !authentication.getPrincipal().equals("anonymousUser")) {
			Cookie cookie = new Cookie(accessToken, "");
			cookie.setMaxAge(0);
			httpServletResponse.addCookie(cookie);
		}

		return ApiResponse.of("로그아웃 성공하였습니다.");
	}

	/**
	 * 사용자 정보를 조회한다.
	 * @author KimJuHyeong
	 * @return MemberGetResponse
	 * @see MemberFacade
	 */
	@GetMapping("/{memberId}")
	@ResponseStatus(OK)
	@ApiOperation(value = "사용자 조회")
	public ApiResponse<MemberGetResponse> getMember(
			@ApiParam(value = "조회할 사용자 아이디", required = true, example = "1")
			@PathVariable("memberId") Long id) {
		return ApiResponse.of(memberFacade.getMember(id));
	}

	/**
	 * 특정 사용자의 배송 정보를 페이지 단위로 조회한다.
	 * @author KimJuHyeong
	 * @param memberId 조회하고자 하는 사용자의 아이디
	 * @return Page<MemberUpdateResponse>
	 * @see MemberFacade
	 */
	@PostMapping(value = "/{memberId}")
	@ResponseStatus(OK)
	@ApiOperation(value = "사용자 수정")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "수정할 이름", required = true, paramType = "form"),
			@ApiImplicitParam(name = "phone", value = "수정할 핸드폰 번호", required = true, paramType = "form"),
			@ApiImplicitParam(name = "password", value = "수정할 비밀번호", required = true, paramType = "form")
	})
	public ApiResponse<MemberUpdateResponse> updateMember(
			@ApiParam(value = "수정할 사용자 아이디", required = true, example = "1")
			@PathVariable Long memberId,

			@ModelAttribute @Valid MemberUpdateRequest memberUpdateRequest,

			@RequestPart(value = "imageFile", required = false) MultipartFile imageFile
	) {
		return ApiResponse.of(
				memberFacade.updateMember(
						toMemberUpdateFacadeRequest(memberId, memberUpdateRequest, imageFile)
				)
		);
	}

	/**
	 * 특정 사용자의 배송 정보를 페이지 단위로 조회한다.
	 * @author KimJuHyeong
	 * @param memberId 조회하고자 하는 사용자의 아이디
	 * @param pageable 조회하고자 하는 페이지 정보
	 * @return Page<DeliveryInfoGetResponse>
	 * @see MemberFacade
	 */
	@GetMapping("/{memberId}/addresses")
	@ResponseStatus(OK)
	@ApiOperation(value = "배송정보 조회")
	public ApiResponse<Page<DeliveryInfoGetResponse>> getDeliveryInfoPage(
			@ApiParam(value = "배송정보를 가진 사용자 아이디", required = true, example = "1")
			@PathVariable("memberId") Long memberId,

			@ApiParam(value = "페이지 네이션을 위한 정보", required = false)
			Pageable pageable
	) {
		return ApiResponse.of(
				memberFacade.getDeliveryInfoPage(memberId, pageable)
		);
	}

	/**
	 * 특정 사용자의 배송 정보를 등록한다.
	 * @author KimJuHyeong
	 * @param deliveryInfoRegisterRequest 배송정보 등록 요청 정보
	 * @return DeliveryInfoRegisterResponse
	 * @see MemberFacade
	 */
	@PostMapping("/{memberId}/addresses")
	@ResponseStatus(OK)
	@ApiOperation(value = "배송정보 등록")
	public ApiResponse<DeliveryInfoRegisterResponse> registerDeliveryInfo(
			@ApiParam(value = "배송정보 등록 요청 정보", required = true)
			@RequestBody @Valid DeliveryInfoRegisterRequest deliveryInfoRegisterRequest
	) {
		return ApiResponse.of(memberFacade.registerDeliveryInfo(deliveryInfoRegisterRequest));
	}

	/**
	 * 특정 사용자의 배송 정보를 페이지 단위로 조회한다.
	 * @author KimJuHyeong
	 * @param deliveryInfoUpdateRequest 배송정보 수정 요청 정보
	 * @return DeliveryInfoUpdateResponse
	 * @see MemberFacade
	 */
	@PutMapping("/{memberId}/addresses")
	@ResponseStatus(OK)
	@ApiOperation(value = "배송정보 수정")
	public ApiResponse<DeliveryInfoUpdateResponse> updateDeliveryInfo(
			@ApiParam(value = "배송정보 수정 요청 정보", required = true)
			@RequestBody @Valid DeliveryInfoUpdateRequest deliveryInfoUpdateRequest
	) {
		return ApiResponse.of(memberFacade.updateDeliveryInfo(deliveryInfoUpdateRequest));
	}

	/**
	 * 특정 사용자의 배송 정보를 삭제한다.
	 * @author KimJuHyeong
	 * @param deliveryInfoDeleteRequest 배송정보 삭제 요청 정보
	 * @return String
	 * @see MemberFacade
	 */
	@DeleteMapping("/{memberId}/addresses")
	@ResponseStatus(OK)
	@ApiOperation(value = "배송정보 삭제")
	public ApiResponse<String> deleteDeliveryInfo(
			@ApiParam(value = "배송정보 삭제 요청 정보", required = true)
			@RequestBody @Valid DeliveryInfoDeleteRequest deliveryInfoDeleteRequest
	) {
		memberFacade.deleteDeliveryInfo(deliveryInfoDeleteRequest);
		return ApiResponse.of("삭제 성공하였습니다.");
	}

	/**
	 * 특정 사용자가 다른 사용자를 팔로잉 목록에 등록한다.
	 * @author KimJuHyeong
	 * @param followingRegisterRequest 팔로잉 등록 요청 정보
	 * @return String
	 * @see MemberFacade
	 */
	@PostMapping("/{memberId}/followings")
	@ResponseStatus(CREATED)
	@ApiOperation(value = "팔로잉 등록")
	public ApiResponse<String> registerFollowing(
			@ApiParam(value = "팔로잉 등록 요청 정보", required = true)
			@RequestBody @Valid FollowingRegisterRequest followingRegisterRequest
	) {
		memberFacade.registerFollowing(followingRegisterRequest);
		return ApiResponse.of("follow 등록에 성공했습니다.");
	}

	/**
	 * 특정 사용자가 다른 사용자를 팔로잉 목록에서 삭제 한다.
	 * @author KimJuHyeong
	 * @param followingDeleteRequest 팔로잉 삭제 요청 정보
	 * @return String
	 * @see MemberFacade
	 */
	@DeleteMapping("/{memberId}/followings")
	@ResponseStatus(OK)
	@ApiOperation(value = "팔로잉 삭제")
	public ApiResponse<String> deleteFollowing(
			@ApiParam(value = "팔로잉 삭제 요청 정보", required = true)
			@RequestBody @Valid FollowingDeleteRequest followingDeleteRequest
	) {
		memberFacade.deleteFollowing(followingDeleteRequest);
		return ApiResponse.of("follow 삭제에 성공했습니다.");
	}

	/**
	 * 특정 사용자의 팔로잉 목록 조회한다.
	 * @author KimJuHyeong
	 * @return FollowingGetAllResponse
	 * @see MemberFacade
	 */
	@GetMapping("/{memberId}/followings")
	@ResponseStatus(OK)
	@ApiOperation(value = "팔로잉 조회")
	public ApiResponse<FollowingGetAllResponse> getAllFollowings() {
		return ApiResponse.of(memberFacade.getAllFollowings());
	}
}
