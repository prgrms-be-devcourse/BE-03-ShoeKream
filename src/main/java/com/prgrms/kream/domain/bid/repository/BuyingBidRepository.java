package com.prgrms.kream.domain.bid.repository;

import com.prgrms.kream.domain.bid.model.BuyingBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyingBidRepository extends JpaRepository<BuyingBid, Long>, BuyingBidCustomRepository{
}
