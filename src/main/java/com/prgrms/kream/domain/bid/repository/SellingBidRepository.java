package com.prgrms.kream.domain.bid.repository;

import com.prgrms.kream.domain.bid.model.SellingBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellingBidRepository extends JpaRepository<SellingBid, Long>, SellingBidCustomRepository {
}
