package com.prgrms.kream.domain.account.model;

import static lombok.AccessLevel.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "transaction_history")
@NoArgsConstructor(access = PROTECTED)
public class TransactionHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "account_id", nullable = false, unique = true)
	private Long accountId;

	@Column(name = "amount", nullable = false, unique = true)
	private int amount;

	@Column(name = "transaction_type", nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Builder
	TransactionHistory(Long id, Long accountId, int amount, TransactionType transactionType) {
		this.id = id;
		this.accountId = accountId;
		this.amount = amount;
		this.transactionType = transactionType;
	}
}
