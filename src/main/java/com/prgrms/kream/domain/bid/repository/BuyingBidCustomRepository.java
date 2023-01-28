package com.prgrms.kream.domain.bid.repository;

import com.prgrms.kream.domain.bid.model.BuyingBid;
import java.util.Optional;

public interface BuyingBidCustomRepository {
	Optional<BuyingBid> findHighestBuyingBidByProductOptionId(Long productOptionId);
}
