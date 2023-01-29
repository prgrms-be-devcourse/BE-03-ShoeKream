package com.prgrms.kream.domain.product.model;

import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.prgrms.kream.common.model.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "product_option")
@NoArgsConstructor(access = PROTECTED)
public class ProductOption extends BaseTimeEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "size", nullable = false, columnDefinition = "SMALLINT")
	private int size;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "highest_price", nullable = false, columnDefinition = "SMALLINT")
	private int highestPrice;

	@Column(name = "lowest_price", nullable = false, columnDefinition = "SMALLINT")
	private int lowestPrice;

	@Builder
	public ProductOption(Long id, Integer size, Product product) {
		this.id = id;
		this.size = size;
		this.product = product;
	}

	public void updateHighestPrice(int price) {
		this.highestPrice = price;
	}
}
