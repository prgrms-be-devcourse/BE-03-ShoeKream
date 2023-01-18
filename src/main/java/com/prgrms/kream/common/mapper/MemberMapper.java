package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.member.dto.MemberRegisterRequest;
import com.prgrms.kream.domain.member.model.Member;

public class MemberMapper {

	public static Member memberRegisterRequestToMember(MemberRegisterRequest memberRegisterRequest) {
		return Member.builder()
				.name(memberRegisterRequest.name())
				.email(memberRegisterRequest.email())
				.phone(memberRegisterRequest.phone())
				.password(memberRegisterRequest.password())
				.authority(memberRegisterRequest.authority())
				.isMale(memberRegisterRequest.isMale())
				.build();
	}

}
