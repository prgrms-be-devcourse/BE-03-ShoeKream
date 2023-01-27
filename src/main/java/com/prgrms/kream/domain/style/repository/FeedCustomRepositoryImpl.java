package com.prgrms.kream.domain.style.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.QFeed;
import com.prgrms.kream.domain.style.model.QFeedTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

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
	public List<Feed> findAllOrderByLikesDesc() {
		return jpaQueryFactory
				.selectFrom(qFeed)
				.orderBy(qFeed.likes.desc(), qFeed.createdAt.desc())
				.fetch();
	}

	private BooleanExpression loeFeedId(Long feedId) {
		return (feedId == null) ? null : qFeed.id.loe(feedId);
	}

}
