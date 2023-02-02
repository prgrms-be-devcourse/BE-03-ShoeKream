package com.prgrms.kream.domain.style.repository;

import java.util.List;

import com.prgrms.kream.domain.style.dto.request.SortType;
import com.prgrms.kream.domain.style.model.Feed;

public interface FeedCustomRepository {

	List<Feed> findAllByTag(String tag, Long cursorId, int pageSize, SortType sortType);

	List<Feed> findAllByMember(Long memberId, Long cursorId, int pageSize, SortType sortType);

	List<Feed> findAllByProduct(Long productId, Long cursorId, int pageSize, SortType sortType);

	List<Feed> findAll(Long cursorId, int pageSize, SortType sortType);

}
