package com.prgrms.kream.domain.style.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.QFeed;
import com.prgrms.kream.domain.style.model.QFeedTag;
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
	public List<Feed> findAllByTag(String tag) {
		return jpaQueryFactory
				.selectFrom(qFeed)
				.where(qFeed.id.in(
						JPAExpressions
								.select(qFeedTag.feedId)
								.from(qFeedTag)
								.where(qFeedTag.tag.eq(tag))))
				.orderBy(qFeed.id.desc())
				.fetch();
	}

	@Override
	public List<Feed> findAllByRecent() {
		return jpaQueryFactory
				.selectFrom(qFeed)
				.orderBy(qFeed.id.desc())
				.fetch();
	}

	@Override
	public List<Feed> findAllByTopLikes() {
		return jpaQueryFactory
				.selectFrom(qFeed)
				.orderBy(qFeed.likes.desc(), qFeed.createdAt.desc())
				.fetch();
	}

}
