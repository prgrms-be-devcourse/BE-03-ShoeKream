package com.prgrms.kream.domain.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.member.model.Following;
import com.prgrms.kream.domain.member.model.FollowingId;

public interface FollowingRepository extends JpaRepository<Following, FollowingId> {
	List<Following> findAllByFollowingId_FollowingMemberId(Long followingMemberId);
}
