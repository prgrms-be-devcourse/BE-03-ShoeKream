package com.prgrms.kream.domain.style.repository;

import java.util.List;

import com.prgrms.kream.domain.style.model.FeedComment;

public interface FeedCommentCustomRepository {

	List<FeedComment> findAllByFeedId(Long feedId, Long cursorId, int pageSize);

}
