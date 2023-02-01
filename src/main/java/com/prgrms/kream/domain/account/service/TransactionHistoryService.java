package com.prgrms.kream.domain.account.service;

import com.prgrms.kream.domain.account.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
	private final TransactionHistoryRepository transactionHistoryRepository;
}
