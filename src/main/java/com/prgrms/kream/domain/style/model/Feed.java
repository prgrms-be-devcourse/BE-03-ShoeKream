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
import com.prgrms.kream.domain.member.model.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "FEED")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", nullable = false, unique = false, length = 255)
	private String content;

	@ManyToOne
	@JoinColumn(name = "author_id", nullable = false, unique = false)
	private Member author;

	@Builder
	public Feed(Long id, String content, Member author) {
		this.id = id;
		this.content = content;
		this.author = author;
	}

	public void updateContent(String content) {
		this.content = content;
	}

}
