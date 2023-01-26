package com.prgrms.kream.domain.style.repository;

import java.util.List;

import com.prgrms.kream.domain.style.model.Feed;

public interface FeedCustomRepository {

	List<Feed> findAllByTag(String tag);

	List<Feed> findAllByMember(Long id);

	List<Feed> findAllOrderByCreatedAtDesc();

	List<Feed> findAllOrderByLikesDesc();

}
