package com.prgrms.kream.domain.style.repository;

import java.util.List;

import com.prgrms.kream.domain.style.model.Feed;

public interface FeedCustomRepository {

	List<Feed> findAllByTag(String tag, Long cursorId, int pageSize);

	List<Feed> findAllByMember(Long memberId, Long cursorId, int pageSize);

	List<Feed> findAllOrderByCreatedAtDesc(Long cursorId, int pageSize);

	List<Feed> findAllOrderByLikesDesc();

}
