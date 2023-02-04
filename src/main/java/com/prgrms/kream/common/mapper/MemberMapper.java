package com.prgrms.kream.common.mapper;

import static lombok.AccessLevel.*;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.prgrms.kream.domain.member.dto.request.DeliveryInfoRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.MemberUpdateFacadeRequest;
import com.prgrms.kream.domain.member.dto.request.MemberUpdateRequest;
import com.prgrms.kream.domain.member.dto.request.MemberUpdateServiceRequest;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoGetResponse;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoUpdateResponse;
import com.prgrms.kream.domain.member.dto.response.MemberGetFacadeResponse;
import com.prgrms.kream.domain.member.dto.response.MemberGetResponse;
import com.prgrms.kream.domain.member.dto.response.MemberUpdateResponse;
import com.prgrms.kream.domain.member.dto.response.MemberUpdateServiceResponse;
import com.prgrms.kream.domain.member.model.DeliveryInfo;
import com.prgrms.kream.domain.member.model.Member;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class MemberMapper {

	public static Member toMember(MemberRegisterRequest memberRegisterRequest) {
		return Member.builder()
				.name(memberRegisterRequest.name())
				.email(memberRegisterRequest.email())
				.phone(memberRegisterRequest.phone())
				.password(memberRegisterRequest.password())
				.authority(memberRegisterRequest.authority())
				.isMale(memberRegisterRequest.isMale())
				.build();
	}

	public static Member toMember(MemberGetFacadeResponse memberGetFacadeResponse) {
		return Member.builder()
				.id(memberGetFacadeResponse.id())
				.name(memberGetFacadeResponse.name())
				.email(memberGetFacadeResponse.email())
				.phone(memberGetFacadeResponse.phone())
				.password(memberGetFacadeResponse.password())
				.authority(memberGetFacadeResponse.authority())
				.build();
	}

	public static MemberGetFacadeResponse toMemberGetFacadeResponse(Member member) {
		return MemberGetFacadeResponse.builder()
				.id(member.getId())
				.name(member.getName())
				.email(member.getEmail())
				.phone(member.getPhone())
				.password(member.getPassword())
				.isMale(member.getIsMale())
				.authority(member.getAuthority())
				.build();
	}

	public static MemberGetResponse toMemberGetResponse(
			MemberGetFacadeResponse memberGetFacadeResponse,
			List<String> imagePaths
	) {
		return MemberGetResponse.builder()
				.id(memberGetFacadeResponse.id())
				.name(memberGetFacadeResponse.name())
				.email(memberGetFacadeResponse.email())
				.phone(memberGetFacadeResponse.phone())
				.imagePaths(imagePaths)
				.build();
	}

	public static MemberUpdateFacadeRequest toMemberUpdateFacadeRequest(
			Long memberId,
			MemberUpdateRequest memberUpdateRequest,
			MultipartFile multipartFile
	) {
		return new MemberUpdateFacadeRequest(
				memberId,
				memberUpdateRequest.name(),
				memberUpdateRequest.phone(),
				memberUpdateRequest.password(),
				multipartFile
		);
	}

	public static MemberUpdateServiceResponse toMemberUpdateServiceResponse(Member member) {
		return new MemberUpdateServiceResponse(member.getId(), member.getName(), member.getPhone());
	}

	public static MemberUpdateServiceRequest toMemberUpdateServiceRequest(
			MemberUpdateFacadeRequest memberUpdateFacadeRequest
	) {
		return new MemberUpdateServiceRequest(
				memberUpdateFacadeRequest.id(),
				memberUpdateFacadeRequest.name(),
				memberUpdateFacadeRequest.phone(),
				memberUpdateFacadeRequest.password()
		);
	}

	public static MemberUpdateResponse toMemberUpdateResponse(
			MemberUpdateServiceResponse memberUpdateServiceResponse,
			List<String> imagePaths
	) {
		return new MemberUpdateResponse(
				memberUpdateServiceResponse.id(),
				memberUpdateServiceResponse.name(),
				memberUpdateServiceResponse.phone(),
				imagePaths
		);
	}

	public static DeliveryInfo toDeliveryInfo(
			DeliveryInfoRegisterRequest deliveryInfoRegisterRequest
	) {
		return DeliveryInfo.builder()
				.name(deliveryInfoRegisterRequest.name())
				.phone(deliveryInfoRegisterRequest.phone())
				.address(deliveryInfoRegisterRequest.address())
				.postCode(deliveryInfoRegisterRequest.postCode())
				.detail(deliveryInfoRegisterRequest.detail())
				.memberId(deliveryInfoRegisterRequest.memberId())
				.build();
	}

	public static DeliveryInfoGetResponse toDeliveryInfoGetResponse(
			DeliveryInfo deliveryInfo
	) {
		return DeliveryInfoGetResponse.builder()
				.id(deliveryInfo.getId())
				.name(deliveryInfo.getName())
				.phone(deliveryInfo.getPhone())
				.postCode(deliveryInfo.getPostCode())
				.address(deliveryInfo.getAddress())
				.detail(deliveryInfo.getDetail())
				.build();
	}

	public static DeliveryInfoUpdateResponse toDeliveryInfoUpdateResponse(DeliveryInfo deliveryInfo) {
		return DeliveryInfoUpdateResponse.builder()
				.name(deliveryInfo.getName())
				.phone(deliveryInfo.getPhone())
				.postCode(deliveryInfo.getPostCode())
				.address(deliveryInfo.getAddress())
				.detail(deliveryInfo.getDetail())
				.build();
	}

}
