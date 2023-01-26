package com.prgrms.kream.domain.style.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.style.model.FeedTag;

public interface FeedTagRepository extends JpaRepository<FeedTag, Long> {

	List<FeedTag> findAllByTag(String tag);

	void deleteAllByFeedId(Long feedId);

}
