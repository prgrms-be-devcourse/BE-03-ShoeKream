package com.prgrms.kream.domain.style.repository;

import java.util.List;

import com.prgrms.kream.domain.style.model.FeedTag;

public interface FeedTagCustomRepository {

	List<Long> saveAllBulk(List<FeedTag> feedTags);

}
