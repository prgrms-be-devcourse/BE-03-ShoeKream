package com.prgrms.kream.domain.member.model;

import static lombok.AccessLevel.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.prgrms.kream.common.model.BaseTimeEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "following")
@NoArgsConstructor(access = PROTECTED)
public class Following extends BaseTimeEntity {
	@EmbeddedId
	private FollowingId followingId;

	public Following(FollowingId followingId) {
		this.followingId = followingId;
	}
}
