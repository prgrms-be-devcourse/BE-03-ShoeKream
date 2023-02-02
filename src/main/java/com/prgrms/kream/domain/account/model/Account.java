package com.prgrms.kream.domain.account.model;

import static lombok.AccessLevel.*;
import com.prgrms.kream.common.exception.BalanceNotEnoughException;
import com.prgrms.kream.common.model.BaseTimeEntity;
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
public class Account extends BaseTimeEntity {
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
	}

	public void updateBalance(TransactionType transactionType, int amount){
		if (amount <= 0){
			throw new IllegalArgumentException();
		}
		if (transactionType == TransactionType.WITHDRAW && balance < amount){
			throw new BalanceNotEnoughException("");
		}
		if (transactionType == TransactionType.DEPOSIT){
			balance += amount;
		}else if(transactionType == TransactionType.WITHDRAW){
			balance -= amount;
		}
	}
}
