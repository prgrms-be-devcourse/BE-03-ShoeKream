package com.prgrms.kream.domain.member.service;

import static com.prgrms.kream.common.jwt.JwtUtil.*;
import static com.prgrms.kream.common.mapper.MemberMapper.*;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.common.exception.DuplicatedEmailException;
import com.prgrms.kream.common.jwt.Jwt;
import com.prgrms.kream.common.mapper.MemberMapper;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoDeleteRequest;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.DeliveryInfoUpdateRequest;
import com.prgrms.kream.domain.member.dto.request.FollowingDeleteRequest;
import com.prgrms.kream.domain.member.dto.request.FollowingRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.MemberLoginRequest;
import com.prgrms.kream.domain.member.dto.request.MemberRegisterRequest;
import com.prgrms.kream.domain.member.dto.request.MemberUpdateServiceRequest;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoGetResponse;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoRegisterResponse;
import com.prgrms.kream.domain.member.dto.response.DeliveryInfoUpdateResponse;
import com.prgrms.kream.domain.member.dto.response.FollowingGetAllResponse;
import com.prgrms.kream.domain.member.dto.response.MemberGetServiceResponse;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	private final DeliveryInfoRepository deliveryInfoRepository;
	private final FollowingRepository followingRepository;

	private final Jwt jwt;

	@Transactional
	public MemberRegisterResponse registerMember(MemberRegisterRequest memberRegisterRequest) {
		if (isDuplicatedEmail(memberRegisterRequest.email())) {
			throw new DuplicatedEmailException("중복된 이메일입니다.");
		}
		Member member = memberRepository.save(
				MemberMapper.toMember(memberRegisterRequest)
		);
		return new MemberRegisterResponse(member.getId());
	}

	@Transactional
	public MemberLoginResponse loginMember(MemberLoginRequest memberLoginRequest) {
		Member member = memberRepository.findByEmail(memberLoginRequest.email())
				.orElseThrow(EntityNotFoundException::new);

		if (member.isNotValidPassword(memberLoginRequest.password())) {
			throw new BadCredentialsException("잘못된 비밀번호 입니다.");
		}
		String[] roles = new String[] {member.getAuthority().name()};

		return new MemberLoginResponse(jwt.sign(Jwt.Claims.from(member.getId(), roles)));
	}

	private boolean isDuplicatedEmail(String email) {
		return memberRepository.existsMemberByEmail(email);
	}

	@Transactional(readOnly = true)
	public MemberGetServiceResponse getMember(Long id) {
		validateAccess(id);
		Member member = getMemberEntity(id);
		return toMemberGetFacadeResponse(member);
	}

	@Transactional
	public MemberUpdateServiceResponse updateMember(MemberUpdateServiceRequest memberUpdateServiceRequest) {
		validateAccess(memberUpdateServiceRequest.id());

		Member member = getMemberEntity(memberUpdateServiceRequest.id());
		member.updateMember(
				memberUpdateServiceRequest.name(),
				memberUpdateServiceRequest.phone(),
				memberUpdateServiceRequest.password()
		);

		return toMemberUpdateServiceResponse(member);
	}

	@Transactional(readOnly = true)
	public Page<DeliveryInfoGetResponse> getDeliveryInfoPage(Long memberId, Pageable pageable) {
		validateAccess(memberId);

		return deliveryInfoRepository.findAllByMemberId(memberId, pageable)
				.map(MemberMapper::toDeliveryInfoGetResponse);
	}

	@Transactional
	public DeliveryInfoRegisterResponse registerDeliveryInfo(DeliveryInfoRegisterRequest deliveryInfoRegisterRequest) {
		validateAccess(deliveryInfoRegisterRequest.memberId());

		DeliveryInfo deliveryInfo = deliveryInfoRepository.save(toDeliveryInfo(deliveryInfoRegisterRequest));
		return new DeliveryInfoRegisterResponse(deliveryInfo.getId());
	}

	@Transactional
	public DeliveryInfoUpdateResponse updateDeliveryInfo(DeliveryInfoUpdateRequest deliveryInfoUpdateRequest) {
		validateAccess(deliveryInfoUpdateRequest.memberId());
		DeliveryInfo deliveryInfo = getDeliveryInfoEntity(deliveryInfoUpdateRequest.deliveryInfoId());

		deliveryInfo.updateDeliveryInfo(
				deliveryInfoUpdateRequest.name(),
				deliveryInfoUpdateRequest.phone(),
				deliveryInfoUpdateRequest.postCode(),
				deliveryInfoUpdateRequest.address(),
				deliveryInfoUpdateRequest.detail()
		);

		return MemberMapper.toDeliveryInfoUpdateResponse(deliveryInfo);
	}

	@Transactional
	public void deleteDeliveryInfo(DeliveryInfoDeleteRequest deliveryInfoDeleteRequest) {
		DeliveryInfo deliveryInfo = getDeliveryInfoEntity(deliveryInfoDeleteRequest.deliveryInfoId());
		validateAccess(deliveryInfo.getMemberId());
		deliveryInfoRepository.deleteById(deliveryInfoDeleteRequest.deliveryInfoId());
	}

	@Transactional
	public void registerFollowing(FollowingRegisterRequest followingRegisterRequest) {
		FollowingId followId = new FollowingId(
				getMemberId(), followingRegisterRequest.followedMemberId()
		);

		if (memberRepository.existsById(followId.getFollowedMemberId())
				&& !followingRepository.existsById(followId)) {
			followingRepository.save(new Following(followId));
		}
	}

	@Transactional
	public void deleteFollowing(FollowingDeleteRequest followingDeleteRequest) {
		FollowingId followingId = new FollowingId(
				followingDeleteRequest.followingMemberId(),
				followingDeleteRequest.followedMemberId()
		);

		Following following = followingRepository.findById(followingId)
				.orElseThrow(() -> new EntityNotFoundException("entity가 존재하지 않습니다."));

		followingRepository.delete(following);
	}

	@Transactional(readOnly = true)
	public FollowingGetAllResponse getAllFollowings() {
		Long followingMemberId = getMemberId();
		List<Long> followedMemberIds = followingRepository.findAllByFollowingId_FollowingMemberId(followingMemberId)
				.stream()
				.map(following -> following
						.getFollowingId()
						.getFollowedMemberId())
				.toList();

		return new FollowingGetAllResponse(followedMemberIds);
	}

	private void validateAccess(Long id) {
		if (!isValidAccess(id)) {
			throw new AccessDeniedException("잘못된 접근입니다");
		}
	}

	private Member getMemberEntity(Long id) {
		return memberRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않은 회원입니다."));
	}

	private DeliveryInfo getDeliveryInfoEntity(Long id) {
		return deliveryInfoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 배송정보입니다."));
	}
}
