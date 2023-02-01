package com.prgrms.kream.domain.account.model;

import static lombok.AccessLevel.*;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "account")
@NoArgsConstructor(access = PROTECTED)
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "member_id", nullable = false, unique = true)
	private Long memberId;

	@Column(name = "balance", nullable = false, unique = false)
	private int balance;

	@Builder
	Account(Long id, Long memberId, int balance) {
		this.id = id;
		this.memberId = memberId;
		this.balance = Objects.requireNonNullElse(balance, 0);
		;
	}
}
