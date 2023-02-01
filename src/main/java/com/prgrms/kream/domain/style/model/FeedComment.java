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
@Table(name = "feed_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedComment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "member_id", nullable = false, unique = false)
	private Long memberId;

	@Column(name = "feed_id", nullable = false, unique = false)
	private Long feedId;

	@Column(name = "content", nullable = false, unique = false, length = 255)
	private String content;

	@Builder
	public FeedComment(Long id, Long memberId, Long feedId, String content) {
		this.id = id;
		this.memberId = memberId;
		this.feedId = feedId;
		this.content = content;
	}

}
