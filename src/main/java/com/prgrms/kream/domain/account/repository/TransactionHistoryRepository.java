package com.prgrms.kream.domain.account.repository;

import com.prgrms.kream.domain.account.dto.response.TransactionHistoryGetResponse;
import com.prgrms.kream.domain.account.model.TransactionHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
	@Query("select th from TransactionHistory th where th.accountId = :accountId")
	List<TransactionHistoryGetResponse> findAllByAccountId(@Param("accountId") Long accountId);
}
