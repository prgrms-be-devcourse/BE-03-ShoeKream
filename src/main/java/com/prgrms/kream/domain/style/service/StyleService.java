package com.prgrms.kream.domain.style.service;

import static com.prgrms.kream.common.mapper.StyleMapper.*;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.style.dto.request.FeedCommentGetServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedGetServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedLikeServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedCommentRegisterServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedRegisterServiceRequest;
import com.prgrms.kream.domain.style.dto.request.FeedUpdateServiceRequest;
import com.prgrms.kream.domain.style.dto.response.FeedCommentGetServiceResponses;
import com.prgrms.kream.domain.style.dto.response.FeedGetServiceResponses;
import com.prgrms.kream.domain.style.dto.response.FeedRegisterServiceResponse;
import com.prgrms.kream.domain.style.dto.response.FeedUpdateServiceResponse;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedComment;
import com.prgrms.kream.domain.style.model.FeedProduct;
import com.prgrms.kream.domain.style.model.FeedTag;
import com.prgrms.kream.domain.style.repository.FeedCommentRepository;
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

	private final FeedCommentRepository feedCommentRepository;

	@Transactional
	public FeedRegisterServiceResponse registerFeed(FeedRegisterServiceRequest feedRegisterServiceRequest) {
		Feed savedFeed = feedRepository.save(
				toFeed(feedRegisterServiceRequest)
		);

		// 태그 추출 및 데이터 삽입
		List<FeedTag> feedTags = TagExtractor.extract(savedFeed).stream().toList();
		if (!feedTags.isEmpty()) {
			feedTagRepository.saveAllBulk(feedTags);
		}

		// 상품 태그 데이터 삽입
		if (feedRegisterServiceRequest.productsIds() != null) {
			List<FeedProduct> feedProducts = toFeedProducts(savedFeed.getId(), feedRegisterServiceRequest.productsIds());
			feedProductRepository.saveAllBulk(feedProducts);
		}

		return toFeedRegisterServiceResponse(savedFeed);
	}

	@Transactional(readOnly = true)
	public FeedGetServiceResponses getAllTrendingFeeds(FeedGetServiceRequest feedGetServiceRequest) {
		List<Feed> feeds = feedRepository.findAllOrderByLikesDesc(
				feedGetServiceRequest.cursorId(),
				feedGetServiceRequest.pageSize()
		);

		return getFeedsOnPageSize(feeds, feedGetServiceRequest.pageSize());
	}

	@Transactional(readOnly = true)
	public FeedGetServiceResponses getAllNewestFeeds(FeedGetServiceRequest feedGetServiceRequest) {
		List<Feed> feeds = feedRepository.findAllOrderByCreatedAtDesc(
				feedGetServiceRequest.cursorId(),
				feedGetServiceRequest.pageSize()
		);

		return getFeedsOnPageSize(feeds, feedGetServiceRequest.pageSize());
	}

	@Transactional(readOnly = true)
	public FeedGetServiceResponses getAllFeedsByTag(FeedGetServiceRequest feedGetServiceRequest, String tag) {
		List<Feed> feeds = feedRepository.findAllByTag(
				tag,
				feedGetServiceRequest.cursorId(),
				feedGetServiceRequest.pageSize()
		);

		return getFeedsOnPageSize(feeds, feedGetServiceRequest.pageSize());
	}

	@Transactional(readOnly = true)
	public FeedGetServiceResponses getAllFeedsByMember(FeedGetServiceRequest feedGetServiceRequest, Long memberId) {
		List<Feed> feeds = feedRepository.findAllByMember(
				memberId,
				feedGetServiceRequest.cursorId(),
				feedGetServiceRequest.pageSize()
		);

		return getFeedsOnPageSize(feeds, feedGetServiceRequest.pageSize());
	}

	@Transactional(readOnly = true)
	public FeedGetServiceResponses getAllFeedsByProduct(FeedGetServiceRequest feedGetServiceRequest, Long productId) {
		List<Feed> feeds = feedRepository.findAllByProduct(
				productId,
				feedGetServiceRequest.cursorId(),
				feedGetServiceRequest.pageSize()
		);

		return getFeedsOnPageSize(feeds, feedGetServiceRequest.pageSize());
	}

	@Transactional
	public FeedUpdateServiceResponse updateFeed(Long id, FeedUpdateServiceRequest feedUpdateServiceRequest) {
		Feed feed = getFeedEntity(id);

		feed.updateContent(feedUpdateServiceRequest.content());
		Feed entity = feedRepository.save(feed);

		// 피드 태그 삭제 후 재등록
		feedTagRepository.deleteAllByFeedId(entity.getId());
		List<FeedTag> feedTags = TagExtractor.extract(entity).stream().toList();
		feedTagRepository.saveAllBulk(feedTags);

		// 피드 상품태그 삭제 후 재등록
		feedProductRepository.deleteAllByFeedId(entity.getId());
		List<FeedProduct> feedProducts = toFeedProducts(entity.getId(), feedUpdateServiceRequest.productIds());
		feedProductRepository.saveAllBulk(feedProducts);

		return toFeedUpdateServiceResponse(feed);
	}

	@Transactional
	public void deleteFeed(Long id) {
		if (feedRepository.existsById(id)) {
			// 관련 테이블의 레코드 삭제 (Cascade)
			feedTagRepository.deleteAllByFeedId(id);
			feedLikeRepository.deleteAllByFeedId(id);
			feedProductRepository.deleteAllByFeedId(id);
			feedCommentRepository.deleteAllByFeedId(id);
			feedRepository.deleteAllById(id);
		}
	}

	@Transactional
	public void registerFeedLike(FeedLikeServiceRequest feedLikeServiceRequest) {
		if (!feedLikeRepository.existsByFeedIdAndMemberId(
				feedLikeServiceRequest.feedId(),
				feedLikeServiceRequest.memberId()
		)) {
			Feed feed = getFeedEntity(feedLikeServiceRequest.feedId());
			feed.increaseLikes();
			feedRepository.save(feed);

			feedLikeRepository.save(toFeedLike(feedLikeServiceRequest));
		}
	}

	@Transactional
	public void deleteFeedLike(FeedLikeServiceRequest feedLikeServiceRequest) {
		if (feedLikeRepository.existsByFeedIdAndMemberId(
				feedLikeServiceRequest.feedId(),
				feedLikeServiceRequest.memberId()
		)) {
			Feed feed = getFeedEntity(feedLikeServiceRequest.feedId());
			feed.decreaseLikes();
			feedRepository.save(feed);

			feedLikeRepository.deleteByFeedIdAndMemberId(
					feedLikeServiceRequest.feedId(),
					feedLikeServiceRequest.memberId()
			);
		}
	}

	@Transactional
	public void registerFeedComment(FeedCommentRegisterServiceRequest feedCommentRegisterServiceRequest) {
		if (feedRepository.existsById(feedCommentRegisterServiceRequest.feedId())) {
			feedCommentRepository.save(toFeedComment(feedCommentRegisterServiceRequest));
			return;
		}
		throw new EntityNotFoundException();
	}

	@Transactional(readOnly = true)
	public FeedCommentGetServiceResponses getAllFeedComments(FeedCommentGetServiceRequest feedCommentGetServiceRequest) {
		if (feedRepository.existsById(feedCommentGetServiceRequest.feedId())) {
			List<FeedComment> feedComments = feedCommentRepository.findAllByFeedId(
					feedCommentGetServiceRequest.feedId(),
					feedCommentGetServiceRequest.cursorId(),
					feedCommentGetServiceRequest.pageSize()
			);

			if (feedComments.size() > feedCommentGetServiceRequest.pageSize()) {
				return toFeedCommentGetServiceResponses(
						feedComments.subList(0, feedComments.size() - 1),
						feedComments.get(feedComments.size() - 1).getId()
				);
			}

			return toFeedCommentGetServiceResponses(feedComments, -1L);
		}

		throw new EntityNotFoundException();
	}

	private FeedGetServiceResponses getFeedsOnPageSize(List<Feed> feeds, Integer pageSize) {
		getFeedProducts(feeds);

		if (feeds.size() > pageSize) {
			return toFeedGetServiceResponses(
					feeds.subList(0, feeds.size() - 1),
					feeds.get(feeds.size() - 1).getId()
			);
		}

		return toFeedGetServiceResponses(feeds, -1L);
	}

	private void getFeedProducts(List<Feed> feeds) {
		feeds.forEach(feed ->
				feed.setProductIds(
						feedProductRepository.findAllByFeedId(feed.getId()).stream()
								.map(FeedProduct::getProductId)
								.toList()));
	}

	private Feed getFeedEntity(long id) {
		return feedRepository.findById(id)
				.orElseThrow(EntityNotFoundException::new);
	}

}
