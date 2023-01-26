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
@Table(name = "feed")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", nullable = false, unique = false, length = 255)
	private String content;

	@Column(name = "author_id", nullable = false, unique = false)
	private Long authorId;

	@Column(name = "likes", nullable = false, unique = false)
	private Long likes;

	@Builder
	public Feed(Long id, String content, Long authorId, Long likes) {
		this.id = id;
		this.content = content;
		this.authorId = authorId;
		this.likes = likes;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void increaseLikes() {
		this.likes += 1;
	}

	public void decreaseLikes() {
		this.likes = (likes > 0) ? likes - 1L: 0;
	}

}
