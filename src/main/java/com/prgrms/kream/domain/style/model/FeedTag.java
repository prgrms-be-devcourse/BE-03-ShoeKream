package com.prgrms.kream.domain.style.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.prgrms.kream.common.model.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "feed_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedTag extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "feed_id", nullable = false, unique = false)
	private Feed feed;

	@Column(name = "tag", nullable = false, unique = false, length = 20)
	private String tag;

	@Builder
	public FeedTag(Long id, Feed feed, String tag) {
		this.id = id;
		this.feed = feed;
		this.tag = tag;
	}

}
