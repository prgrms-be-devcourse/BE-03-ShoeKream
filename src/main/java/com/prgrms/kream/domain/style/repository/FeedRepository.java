package com.prgrms.kream.domain.style.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.style.model.Feed;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedCustomRepository {
}
