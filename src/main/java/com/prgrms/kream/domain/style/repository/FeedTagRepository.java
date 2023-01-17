package com.prgrms.kream.domain.style.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedTag;

public interface FeedTagRepository extends JpaRepository<FeedTag, Long> {

	void deleteAllByFeed(Feed feed);

}
