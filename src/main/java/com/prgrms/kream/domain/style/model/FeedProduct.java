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
@Table(name = "feed_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedProduct extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "feed_id", nullable = false, unique = false)
	private Long feedId;

	@Column(name = "product_id", nullable = false, unique = false)
	private Long productId;

	@Builder
	public FeedProduct(Long id, Long feedId, Long productId) {
		this.id = id;
		this.feedId = feedId;
		this.productId = productId;
	}

}
