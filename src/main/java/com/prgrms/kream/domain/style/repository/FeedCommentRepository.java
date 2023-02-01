package com.prgrms.kream.domain.style.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prgrms.kream.domain.style.model.FeedComment;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {

	@Modifying(clearAutomatically = true)
	@Query("delete from FeedComment feedComment where feedComment.feedId = :feedId")
	void deleteAllByFeedId(Long feedId);

}
