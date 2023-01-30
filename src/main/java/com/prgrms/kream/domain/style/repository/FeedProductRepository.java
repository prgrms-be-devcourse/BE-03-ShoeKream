package com.prgrms.kream.domain.style.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prgrms.kream.domain.style.model.FeedProduct;

public interface FeedProductRepository extends JpaRepository<FeedProduct, Long>, FeedProductCustomRepository {

	List<FeedProduct> findAllByFeedId(Long feedId);

	@Modifying
	@Query("delete from FeedProduct feedProduct where feedProduct.feedId = :feedId")
	void deleteAllByFeedId(Long feedId);

}
