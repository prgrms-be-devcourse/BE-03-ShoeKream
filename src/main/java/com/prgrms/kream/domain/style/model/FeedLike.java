package com.prgrms.kream.domain.style.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prgrms.kream.common.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "feed_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedLike extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "feed_id", nullable = false, unique = false)
	private Long feedId;

	@Column(name = "member_id", nullable = false, unique = false)
	private Long memberId;

	@Builder
	public FeedLike(Long id, Long feedId, Long memberId) {
		this.id = id;
		this.feedId = feedId;
		this.memberId = memberId;
	}

}
