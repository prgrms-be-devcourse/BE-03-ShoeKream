package com.prgrms.kream.common.mapper;

import static lombok.AccessLevel.*;

import com.prgrms.kream.domain.member.dto.MemberRegisterRequest;
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
}
