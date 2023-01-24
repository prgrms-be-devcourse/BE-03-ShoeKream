package com.prgrms.kream.domain.style.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.style.model.FeedLike;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

	long countByFeedId(Long feedId);

	boolean existsByFeedIdAndMemberId(Long feedId, Long memberId);

	void deleteAllByFeedId(Long feedId);

	void deleteByFeedIdAndMemberId(Long feedId, Long memberId);

}
