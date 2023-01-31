package com.prgrms.kream.domain.style.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prgrms.kream.domain.style.model.FeedTag;

public interface FeedTagRepository extends JpaRepository<FeedTag, Long>, FeedTagCustomRepository {

	@Modifying
	@Query("delete from FeedTag feedTag where feedTag.feedId = :feedId")
	void deleteAllByFeedId(Long feedId);

}
