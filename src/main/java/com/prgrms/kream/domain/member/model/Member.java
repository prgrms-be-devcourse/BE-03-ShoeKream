package com.prgrms.kream.domain.member.model;

import static javax.persistence.EnumType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prgrms.kream.common.model.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, unique = false, length = 20)
	private String name;

	@Column(name = "email", nullable = false, unique = true, length = 20)
	private String email;

	@Embedded
	private Phone phone;

	@Embedded
	private Password password;

	@Column(name = "is_male", nullable = false, unique = false)
	private Boolean isMale;

	@Enumerated(value = STRING)
	@Column(name = "authority", nullable = false, unique = false)
	private Authority authority;

	@Builder
	public Member(Long id, String name, String email, String phone, String password, Boolean isMale,
			Authority authority) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = new Phone(phone);
		this.password = new Password(password);
		this.isMale = isMale;
		this.authority = authority;
	}
}