package com.prgrms.kream.domain.bid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prgrms.kream.domain.bid.model.BuyingBid;

@Repository
public interface BuyingBidRepository extends JpaRepository<BuyingBid, Long>, BuyingBidCustomRepository{
}
