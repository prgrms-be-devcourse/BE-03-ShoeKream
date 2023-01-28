package com.prgrms.kream.domain.bid.repository;

import com.prgrms.kream.domain.bid.model.SellingBid;
import java.util.Optional;

public interface SellingBidCustomRepository {
	Optional<SellingBid> findLowestSellingBidByProductOptionId(Long productOptionId);
}
