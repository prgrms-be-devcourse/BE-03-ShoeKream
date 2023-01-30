package com.prgrms.kream.domain.style.repository;

import java.util.List;

import com.prgrms.kream.domain.style.model.FeedProduct;

public interface FeedProductCustomRepository {

	List<Long> saveAllBulk(List<FeedProduct> feedProducts);

}
