package com.prgrms.kream.domain.style.service;

import static com.prgrms.kream.common.mapper.StyleMapper.*;

import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.style.dto.request.RegisterFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedServiceResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedServiceResponse;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedTag;
import com.prgrms.kream.domain.style.repository.FeedRepository;
import com.prgrms.kream.domain.style.repository.FeedTagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StyleService {

	private final FeedRepository feedRepository;

	private final FeedTagRepository feedTagRepository;

	@Transactional
	public RegisterFeedServiceResponse register(RegisterFeedServiceRequest registerFeedServiceRequest) {
		Feed savedFeed = feedRepository.save(
				toFeed(registerFeedServiceRequest)
		);

		// 태그 추출 및 데이터 삽입
		Set<FeedTag> feedTags = TagExtractor.extract(savedFeed);
		feedTagRepository.saveAll(feedTags);

		return toRegisterFeedServiceResponse(savedFeed);
	}

	@Transactional
	public UpdateFeedServiceResponse update(long id, UpdateFeedServiceRequest updateFeedServiceRequest) {
		Feed updatedFeed = feedRepository.findById(id)
				.map(feed -> {
					feed.updateContent(updateFeedServiceRequest.content());
					Feed entity = feedRepository.save(feed);

					feedTagRepository.deleteAllByFeed(entity);

					Set<FeedTag> feedTags = TagExtractor.extract(entity);
					feedTagRepository.saveAll(feedTags);

					return entity;
				})
				.orElseThrow(EntityNotFoundException::new);

		return toUpdateFeedServiceResponse(updatedFeed);
	}

	@Transactional
	public void delete(long id) {
		feedRepository.findById(id)
				.ifPresent(feed -> {
					// 관련 테이블의 레코드 삭제 (Cascade)
					feedTagRepository.deleteAllByFeed(feed);
					feedRepository.delete(feed);
				});
	}

}
