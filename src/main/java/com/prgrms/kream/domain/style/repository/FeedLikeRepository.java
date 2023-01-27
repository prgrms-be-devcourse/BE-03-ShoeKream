package com.prgrms.kream.domain.style.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prgrms.kream.domain.style.model.FeedLike;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

	boolean existsByFeedIdAndMemberId(Long feedId, Long memberId);

	@Modifying
	@Query("delete from FeedLike feedlike where feedlike.feedId = :feedId")
	void deleteAllByFeedId(Long feedId);

	void deleteByFeedIdAndMemberId(Long feedId, Long memberId);

}
