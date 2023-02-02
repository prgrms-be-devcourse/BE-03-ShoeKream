package com.prgrms.kream.domain.account.service;

import static com.prgrms.kream.common.mapper.AccountMapper.*;
import com.prgrms.kream.domain.account.dto.request.TransactionHistoryCreateRequest;
import com.prgrms.kream.domain.account.dto.request.TransactionHistoryGetFacadeRequest;
import com.prgrms.kream.domain.account.dto.request.TransactionHistoryGetServiceRequest;
import com.prgrms.kream.domain.account.dto.response.TransactionHistoryCreateResponse;
import com.prgrms.kream.domain.account.dto.response.TransactionHistoryGetResponse;
import com.prgrms.kream.domain.account.model.TransactionHistory;
import com.prgrms.kream.domain.account.repository.TransactionHistoryRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
	private final TransactionHistoryRepository transactionHistoryRepository;

	@Transactional
	public TransactionHistoryCreateResponse register(TransactionHistoryCreateRequest transactionHistoryCreateRequest) {
		return toTransactionHistoryCreateResponse(
				transactionHistoryRepository.save(toTransactionHistory(transactionHistoryCreateRequest)));
	}

	@Transactional(readOnly = true)
	public List<TransactionHistoryGetResponse> getAll(
			TransactionHistoryGetServiceRequest transactionHistoryGetServiceRequest){
		return transactionHistoryRepository.findAllByAccountId(transactionHistoryGetServiceRequest.accountId());
	}

	private TransactionHistory findTransactionHistoryEntityById(Long id) {
		return transactionHistoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
