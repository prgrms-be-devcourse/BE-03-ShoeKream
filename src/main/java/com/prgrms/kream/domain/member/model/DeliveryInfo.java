package com.prgrms.kream.domain.member.model;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prgrms.kream.common.model.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "delivery_info")
@NoArgsConstructor(access = PROTECTED)
public class DeliveryInfo extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = false, length = 20)
	private String name;

	@Embedded
	private Phone phone;

	@Column(name = "post_code", nullable = false, unique = false, length = 10)
	private String postCode;

	@Column(name = "address", nullable = false, unique = false, length = 50)
	private String address;

	@Column(name = "detail", nullable = false, unique = false, length = 30)
	private String detail;

	@Column(name = "member_id", nullable = false, unique = false)
	private Long memberId;

	@Builder
	public DeliveryInfo(Long id, String name, Phone phone, String postCode, String address, String detail, Long memberId) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.postCode = postCode;
		this.address = address;
		this.detail = detail;
		this.memberId = memberId;
	}
}