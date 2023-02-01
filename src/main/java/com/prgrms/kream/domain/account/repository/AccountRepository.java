package com.prgrms.kream.domain.account.repository;

import com.prgrms.kream.domain.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	@Query("select a from Account a where a.memberId = :memberId")
	Account findByMemberId(@Param("memberId") Long memberId);
}
