package com.prgrms.kream.domain.style.repository;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.QFeed;
import com.prgrms.kream.domain.style.model.QFeedTag;
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

	@Override
	public List<Feed> findAllByTag(String tag, Long cursorId, int pageSize) {
		return jpaQueryFactory
				.selectFrom(qFeed)
				.where(qFeed.id.in(
						JPAExpressions
								.select(qFeedTag.feedId)
								.from(qFeedTag)
								.where(qFeedTag.tag.eq(tag))),
						loeFeedId(cursorId)
				)
				.orderBy(qFeed.id.desc())
				.limit(pageSize+1)
				.fetch();
	}

	@Override
	public List<Feed> findAllByMember(Long memberId, Long cursorId, int pageSize) {
		return jpaQueryFactory
				.selectFrom(qFeed)
				.where(
						qFeed.authorId.eq(memberId),
						loeFeedId(cursorId)
				)
				.orderBy(qFeed.id.desc())
				.limit(pageSize+1)
				.fetch();
	}

	@Override
	public List<Feed> findAllOrderByCreatedAtDesc(Long cursorId, int pageSize) {
		return jpaQueryFactory
				.selectFrom(qFeed)
				.where(loeFeedId(cursorId))
				.orderBy(qFeed.id.desc())
				.limit(pageSize+1)
				.fetch();
	}

	@Override
	public List<Feed> findAllOrderByLikesDesc(Long cursorId, int pageSize) {
		Feed feed = jpaQueryFactory
				.selectFrom(qFeed)
				.where(eqFeedId(cursorId))
				.orderBy(qFeed.likes.desc(), qFeed.id.desc())
				.fetchFirst();

		if (feed == null) {
			return Collections.emptyList();
		}

		return jpaQueryFactory
				.selectFrom(qFeed)
				.where(ltLikesOrEqLikesAndLoeFeedId(feed.getLikes(), feed.getId()))
				.orderBy(qFeed.likes.desc(), qFeed.id.desc())
				.limit(pageSize+1)
				.fetch();
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
