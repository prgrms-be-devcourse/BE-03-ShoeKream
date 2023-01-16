package com.prgrms.kream.domain.style.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.common.mapper.StyleMapper;
import com.prgrms.kream.domain.style.dto.CreateFeedRequestOfService;
import com.prgrms.kream.domain.style.dto.FeedResponse;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.repository.FeedRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StyleService {

	private final FeedRepository feedRepository;

	@Transactional
	public FeedResponse register(CreateFeedRequestOfService request) {
		Feed savedFeed = feedRepository.save(StyleMapper.toEntity(request));

		return StyleMapper.toDto(savedFeed);
	}

	@Transactional
	public void delete(long id) {
		feedRepository.findById(id)
				.ifPresent(feed -> {
					// 관련 테이블의 레코드 삭제 (Cascade)
					feedRepository.delete(feed);
				});
	}

}
