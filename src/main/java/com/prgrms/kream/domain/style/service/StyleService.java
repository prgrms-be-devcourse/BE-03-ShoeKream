package com.prgrms.kream.domain.style.service;

import static com.prgrms.kream.common.mapper.StyleMapper.*;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.style.dto.request.GetFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.LikeFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.response.GetFeedServiceResponses;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedServiceResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedServiceResponse;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedProduct;
import com.prgrms.kream.domain.style.model.FeedTag;
import com.prgrms.kream.domain.style.repository.FeedLikeRepository;
import com.prgrms.kream.domain.style.repository.FeedProductRepository;
import com.prgrms.kream.domain.style.repository.FeedRepository;
import com.prgrms.kream.domain.style.repository.FeedTagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StyleService {

	private final FeedRepository feedRepository;

	private final FeedTagRepository feedTagRepository;

	private final FeedLikeRepository feedLikeRepository;

	private final FeedProductRepository feedProductRepository;

	@Transactional
	public RegisterFeedServiceResponse register(RegisterFeedServiceRequest registerFeedServiceRequest) {
		Feed savedFeed = feedRepository.save(
				toFeed(registerFeedServiceRequest)
		);

		// 태그 추출 및 데이터 삽입
		List<FeedTag> feedTags = TagExtractor.extract(savedFeed).stream().toList();
		if (!feedTags.isEmpty()) {
			feedTagRepository.saveAllBulk(feedTags);
		}

		// 상품 태그 데이터 삽입
		if (registerFeedServiceRequest.productsIds() != null) {
			List<FeedProduct> feedProducts = toFeedProducts(savedFeed.getId(), registerFeedServiceRequest.productsIds());
			feedProductRepository.saveAllBulk(feedProducts);
		}

		return toRegisterFeedServiceResponse(savedFeed);
	}

	@Transactional(readOnly = true)
	public GetFeedServiceResponses getTrendingFeeds(GetFeedServiceRequest getFeedServiceRequest) {
		List<Feed> feeds = feedRepository.findAllOrderByLikesDesc(
				getFeedServiceRequest.cursorId(),
				getFeedServiceRequest.pageSize()
		);

		return getFeedsOnPageSize(feeds, getFeedServiceRequest.pageSize());
	}

	@Transactional(readOnly = true)
	public GetFeedServiceResponses getNewestFeeds(GetFeedServiceRequest getFeedServiceRequest) {
		List<Feed> feeds = feedRepository.findAllOrderByCreatedAtDesc(
				getFeedServiceRequest.cursorId(),
				getFeedServiceRequest.pageSize()
		);

		return getFeedsOnPageSize(feeds, getFeedServiceRequest.pageSize());
	}

	@Transactional(readOnly = true)
	public GetFeedServiceResponses getAllByTag(GetFeedServiceRequest getFeedServiceRequest, String tag) {
		List<Feed> feeds = feedRepository.findAllByTag(
				tag,
				getFeedServiceRequest.cursorId(),
				getFeedServiceRequest.pageSize()
		);

		return getFeedsOnPageSize(feeds, getFeedServiceRequest.pageSize());
	}

	@Transactional(readOnly = true)
	public GetFeedServiceResponses getAllByMember(GetFeedServiceRequest getFeedServiceRequest, Long memberId) {
		List<Feed> feeds = feedRepository.findAllByMember(
				memberId,
				getFeedServiceRequest.cursorId(),
				getFeedServiceRequest.pageSize()
		);

		return getFeedsOnPageSize(feeds, getFeedServiceRequest.pageSize());
	}

	@Transactional(readOnly = true)
	public GetFeedServiceResponses getAllByProduct(GetFeedServiceRequest getFeedServiceRequest, Long productId) {
		List<Feed> feeds = feedRepository.findAllByProduct(
				productId,
				getFeedServiceRequest.cursorId(),
				getFeedServiceRequest.pageSize()
		);

		return getFeedsOnPageSize(feeds, getFeedServiceRequest.pageSize());
	}

	@Transactional
	public UpdateFeedServiceResponse update(Long id, UpdateFeedServiceRequest updateFeedServiceRequest) {
		Feed updatedFeed = feedRepository.findById(id)
				.map(feed -> {
					feed.updateContent(updateFeedServiceRequest.content());
					Feed entity = feedRepository.save(feed);

					// 피드 태그 삭제 후 재등록
					feedTagRepository.deleteAllByFeedId(entity.getId());
					List<FeedTag> feedTags = TagExtractor.extract(entity).stream().toList();
					feedTagRepository.saveAllBulk(feedTags);

					// 피드 상품태그 삭제 후 재등록
					feedProductRepository.deleteAllByFeedId(entity.getId());
					List<FeedProduct> feedProducts = toFeedProducts(entity.getId(), updateFeedServiceRequest.productIds());
					feedProductRepository.saveAllBulk(feedProducts);

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
					feedProductRepository.deleteAllByFeedId(feed.getId());
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

	private GetFeedServiceResponses getFeedsOnPageSize(List<Feed> feeds, Integer pageSize) {
		getFeedProductsOnFeeds(feeds);

		if (feeds.size() > pageSize) {
			return toGetFeedServiceResponses(
					feeds.subList(0, feeds.size() - 1),
					feeds.get(feeds.size() - 1).getId()
			);
		}

		return toGetFeedServiceResponses(feeds, -1L);
	}

	private void getFeedProductsOnFeeds(List<Feed> feeds) {
		feeds.forEach(feed ->
				feed.setProductIds(
						feedProductRepository.findAllByFeedId(feed.getId()).stream()
								.map(FeedProduct::getProductId)
								.toList()));
	}

}
