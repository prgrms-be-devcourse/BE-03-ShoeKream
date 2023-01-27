package com.prgrms.kream.domain.bid.repository;

import com.prgrms.kream.domain.bid.model.SellingBid;
import java.util.List;

public interface SellingBidCustomRepository {
	List<SellingBid> findLowestSellingBidByProductOptionId(Long productOptionId);
}
