package com.prgrms.kream.domain.style.service;

import static com.prgrms.kream.common.mapper.StyleMapper.*;

import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.style.dto.request.LikeFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.response.GetFeedServiceResponses;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedServiceResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedServiceResponse;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedTag;
import com.prgrms.kream.domain.style.repository.FeedLikeRepository;
import com.prgrms.kream.domain.style.repository.FeedRepository;
import com.prgrms.kream.domain.style.repository.FeedTagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StyleService {

	private final FeedRepository feedRepository;

	private final FeedTagRepository feedTagRepository;

	private final FeedLikeRepository feedLikeRepository;

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

	@Transactional(readOnly = true)
	public GetFeedServiceResponses getTrendingFeeds() {
		return toGetFeedServiceResponses(feedRepository.findAllOrderByLikesDesc());
	}

	@Transactional(readOnly = true)
	public GetFeedServiceResponses getNewestFeeds() {
		return toGetFeedServiceResponses(feedRepository.findAllOrderByCreatedAtDesc());
	}

	@Transactional(readOnly = true)
	public GetFeedServiceResponses getAllByTag(String tag) {
		return toGetFeedServiceResponses(feedRepository.findAllByTag(tag));
	}

	@Transactional(readOnly = true)
	public GetFeedServiceResponses getAllByMember(Long id) {
		return toGetFeedServiceResponses(feedRepository.findAllByMember(id));
	}

	@Transactional
	public UpdateFeedServiceResponse update(Long id, UpdateFeedServiceRequest updateFeedServiceRequest) {
		Feed updatedFeed = feedRepository.findById(id)
				.map(feed -> {
					feed.updateContent(updateFeedServiceRequest.content());
					Feed entity = feedRepository.save(feed);

					feedTagRepository.deleteAllByFeedId(entity.getId());

					Set<FeedTag> feedTags = TagExtractor.extract(entity);
					feedTagRepository.saveAll(feedTags);

					return entity;
				})
				.orElseThrow(EntityNotFoundException::new);

		return toUpdateFeedServiceResponse(updatedFeed);
	}

	@Transactional
	public void delete(Long id) {
		feedRepository.findById(id)
				.ifPresent(feed -> {
					// 관련 테이블의 레코드 삭제 (Cascade)
					feedTagRepository.deleteAllByFeedId(feed.getId());
					feedLikeRepository.deleteAllByFeedId(feed.getId());
					feedRepository.delete(feed);
				});
	}

	@Transactional
	public void registerFeedLike(LikeFeedServiceRequest likeFeedServiceRequest) {
		if (!feedLikeRepository.existsByFeedIdAndMemberId(
				likeFeedServiceRequest.feedId(),
				likeFeedServiceRequest.memberId()
		)) {
			feedRepository.findById(likeFeedServiceRequest.feedId())
					.map(feed -> {
						feed.increaseLikes();
						return feedRepository.save(feed);
					})
					.orElseThrow(EntityNotFoundException::new);

			feedLikeRepository.save(toFeedLike(likeFeedServiceRequest));
		}
	}

	@Transactional
	public void deleteFeedLike(LikeFeedServiceRequest likeFeedServiceRequest) {
		if (feedLikeRepository.existsByFeedIdAndMemberId(
				likeFeedServiceRequest.feedId(),
				likeFeedServiceRequest.memberId()
		)) {
			feedRepository.findById(likeFeedServiceRequest.feedId())
					.map(feed -> {
						feed.decreaseLikes();
						return feedRepository.save(feed);
					})
					.orElseThrow(EntityNotFoundException::new);

			feedLikeRepository.deleteByFeedIdAndMemberId(
					likeFeedServiceRequest.feedId(),
					likeFeedServiceRequest.memberId()
			);
		}
	}

}
