package com.prgrms.kream.domain.bid.repository;

import com.prgrms.kream.domain.bid.model.BuyingBid;
import java.util.List;

public interface BuyingBidCustomRepository {
	List<BuyingBid> findHighestBuyingBidByProductOptionId(Long productOptionId);
}
