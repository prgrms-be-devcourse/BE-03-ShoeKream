package com.prgrms.kream.domain.style.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.prgrms.kream.domain.style.dto.request.SortType;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.QFeed;
import com.prgrms.kream.domain.style.model.QFeedProduct;
import com.prgrms.kream.domain.style.model.QFeedTag;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FeedCustomRepositoryImpl implements FeedCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	QFeed qFeed = QFeed.feed;

	QFeedTag qFeedTag = QFeedTag.feedTag;

	QFeedProduct qFeedProduct = QFeedProduct.feedProduct;

	@Override
	public List<Feed> findAllByTag(String tag, Long cursorId, int pageSize, SortType sortType) {
		Feed feed = null;
		if (sortType == SortType.POPULAR) {
			feed = findTopByLikes(cursorId);

			if (feed == null) {
				return Collections.emptyList();
			}
		}

		return jpaQueryFactory
				.selectFrom(qFeed)
				.where(qFeed.id.in(
						JPAExpressions
								.select(qFeedTag.feedId)
								.from(qFeedTag)
								.where(qFeedTag.tag.eq(tag))),
						dynamicWhereClause(cursorId, feed, sortType)
				)
				.orderBy(dynamicOrderByClause(sortType))
				.limit(pageSize+1)
				.fetch();
	}

	@Override
	public List<Feed> findAllByMember(Long memberId, Long cursorId, int pageSize, SortType sortType) {
		Feed feed = null;
		if (sortType == SortType.POPULAR) {
			feed = findTopByLikes(cursorId);

			if (feed == null) {
				return Collections.emptyList();
			}
		}

		return jpaQueryFactory
				.selectFrom(qFeed)
				.where(
						qFeed.authorId.eq(memberId),
						dynamicWhereClause(cursorId, feed, sortType)
				)
				.orderBy(dynamicOrderByClause(sortType))
				.limit(pageSize+1)
				.fetch();
	}

	@Override
	public List<Feed> findAllByProduct(Long productId, Long cursorId, int pageSize, SortType sortType) {
		Feed feed = null;
		if (sortType == SortType.POPULAR) {
			feed = findTopByLikes(cursorId);

			if (feed == null) {
				return Collections.emptyList();
			}
		}

		return jpaQueryFactory
				.selectFrom(qFeed)
				.where(qFeed.id.in(
						JPAExpressions
								.select(qFeedProduct.feedId)
								.from(qFeedProduct)
								.where(qFeedProduct.productId.eq(productId))),
						dynamicWhereClause(cursorId, feed, sortType)
				)
				.orderBy(dynamicOrderByClause(sortType))
				.limit(pageSize+1)
				.fetch();
	}

	@Override
	public List<Feed> findAll(Long cursorId, int pageSize, SortType sortType) {
		Feed feed = null;
		if (sortType == SortType.POPULAR) {
			feed = findTopByLikes(cursorId);

			if (feed == null) {
				return Collections.emptyList();
			}
		}

		return jpaQueryFactory
				.selectFrom(qFeed)
				.where(dynamicWhereClause(cursorId, feed, sortType))
				.orderBy(dynamicOrderByClause(sortType))
				.limit(pageSize+1)
				.fetch();
	}

	private Feed findTopByLikes(Long cursorId) {
		return jpaQueryFactory
				.selectFrom(qFeed)
				.where(eqFeedId(cursorId))
				.orderBy(qFeed.likes.desc(), qFeed.id.desc())
				.fetchFirst();
	}

	private OrderSpecifier<?>[] dynamicOrderByClause(SortType sortType) {
		return switch (sortType) {
			case POPULAR -> new OrderSpecifier[] {
					new OrderSpecifier(Order.DESC, qFeed.likes),
					new OrderSpecifier(Order.DESC, qFeed.id)
			};
			case NEWEST -> new OrderSpecifier[] {
					new OrderSpecifier(Order.DESC, qFeed.id)
			};
		};
	}

	private BooleanExpression dynamicWhereClause(Long cursorId, Feed feed, SortType sortType) {
		return switch (sortType) {
			case POPULAR -> ltLikesOrEqLikesAndLoeFeedId(feed.getLikes(), cursorId);
			case NEWEST -> loeFeedId(cursorId);
		};
	}

	private BooleanExpression loeFeedId(Long feedId) {
		return (feedId == null) ? null : qFeed.id.loe(feedId);
	}

	private BooleanExpression eqFeedId(Long feedId) {
		return (feedId == null) ? null : qFeed.id.eq(feedId);
	}

	private BooleanExpression ltLikesOrEqLikesAndLoeFeedId(Long likes, Long feedId) {
		if (likes == null) {
			log.error("피드 조회 중 '좋아요' 관련하여 feedId = {}, likes = {} 값에서 오류가 발생했습니다.", feedId, likes);
			throw new IllegalArgumentException("피드 조회 중 '좋아요' 관련하여 내부적인 오류가 발생했습니다.");
		}

		return (feedId == null) ? null : qFeed.likes.lt(likes).or(qFeed.likes.eq(likes).and(qFeed.id.loe(feedId)));
	}

}
