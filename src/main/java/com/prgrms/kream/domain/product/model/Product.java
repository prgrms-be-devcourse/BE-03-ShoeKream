package com.prgrms.kream.domain.product.model;

import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prgrms.kream.common.model.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseTimeEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "release_price", nullable = false)
	private int releasePrice;

	@Column(name = "description", nullable = false)
	private String description;

	@Builder
	public Product(Long id, String name, int releasePrice, String description) {
		this.id = id;
		this.name = name;
		this.releasePrice = releasePrice;
		this.description = description;
	}
}
