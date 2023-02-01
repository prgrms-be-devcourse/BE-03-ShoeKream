package com.prgrms.kream.domain.style.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prgrms.kream.domain.style.model.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedCustomRepository {

	@Modifying
	@Query("delete from Feed feed where feed.id = :id")
	void deleteAllById(Long id);

}
