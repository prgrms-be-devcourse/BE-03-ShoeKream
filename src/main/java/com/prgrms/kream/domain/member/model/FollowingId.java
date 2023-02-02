package com.prgrms.kream.domain.member.model;

import static lombok.AccessLevel.*;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class FollowingId implements Serializable {
	@Column(name = "following_member_id", nullable = false, unique = false)
	private Long followingMemberId;

	@Column(name = "followed_member_id", nullable = false, unique = false)
	private Long followedMemberId;

	public FollowingId(Long followingMemberId, Long followedMemberId) {
		this.followingMemberId = followingMemberId;
		this.followedMemberId = followedMemberId;
	}
}
