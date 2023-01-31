package com.prgrms.kream.domain.member.facade;

import static com.prgrms.kream.common.mapper.MemberMapper.*;
import static com.prgrms.kream.domain.image.model.DomainType.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.image.service.ImageService;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoUpdateRequest;
import com.prgrms.kream.domain.member.dto.request.MemberLoginRequest;
import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.MemberUpdateFacadeRequest;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoGetResponse;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoRegisterResponse;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoUpdateResponse;
import com.prgrms.kream.domain.member.dto.response.MemberGetFacadeResponse;
import com.prgrms.kream.domain.member.dto.response.MemberGetResponse;
import com.prgrms.kream.domain.member.dto.response.MemberLoginResponse;
import com.prgrms.kream.domain.member.dto.response.MemberRegisterResponse;
import com.prgrms.kream.domain.member.dto.response.MemberUpdateResponse;
import com.prgrms.kream.domain.member.dto.response.MemberUpdateServiceResponse;
import com.prgrms.kream.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFacade {
	private final MemberService memberService;
	private final ImageService imageService;

	public MemberRegisterResponse register(MemberRegisterRequest memberRegisterRequest) {
		return memberService.register(memberRegisterRequest);
	}

	public MemberLoginResponse login(MemberLoginRequest memberLoginRequest) {
		return memberService.login(memberLoginRequest);
	}

	@Transactional(readOnly = true)
	public MemberGetResponse get(Long memberId) {
		MemberGetFacadeResponse memberGetFacadeResponse = memberService.get(memberId);
		List<String> imagePaths = imageService.getAll(memberId, MEMBER);
		return toMemberGetResponse(memberGetFacadeResponse, imagePaths);
	}

	@Transactional
	public MemberUpdateResponse updateMember(MemberUpdateFacadeRequest memberUpdateFacadeRequest) {
		MemberUpdateServiceResponse memberUpdateServiceResponse =
				memberService.updateMember(toMemberUpdateServiceRequest(memberUpdateFacadeRequest));

		List<String> imagePaths = Collections.emptyList();
		imageService.deleteAllByReference(memberUpdateFacadeRequest.id(), MEMBER);
		if (!Objects.isNull(memberUpdateFacadeRequest.imageFile())) {
			imageService.register(List.of(memberUpdateFacadeRequest.imageFile()), memberUpdateFacadeRequest.id(), MEMBER);
			imagePaths = imageService.getAll(memberUpdateFacadeRequest.id(), MEMBER);
		}
		return toMemberUpdateResponse(memberUpdateServiceResponse, imagePaths);
	}

	public Page<DeliveryInfoGetResponse> getDeliveryInfoPage(Long memberId, Pageable pageable) {
		return memberService.getDeliveryInfoPage(memberId, pageable);
	}

	public DeliveryInfoRegisterResponse registerDeliveryInfo(DeliveryInfoRegisterRequest deliveryInfoRegisterRequest) {
		return memberService.registerDeliveryInfo(deliveryInfoRegisterRequest);
	}

	public DeliveryInfoUpdateResponse updateDeliveryInfo(DeliveryInfoUpdateRequest deliveryInfoRegisterRequest) {
		return memberService.updateDeliveryInfo(deliveryInfoRegisterRequest);
	}
}
