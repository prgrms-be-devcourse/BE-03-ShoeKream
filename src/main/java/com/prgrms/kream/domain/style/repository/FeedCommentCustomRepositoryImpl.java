package com.prgrms.kream.domain.style.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prgrms.kream.domain.style.model.FeedComment;
import com.prgrms.kream.domain.style.model.QFeedComment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FeedCommentCustomRepositoryImpl implements FeedCommentCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	QFeedComment qFeedComment = QFeedComment.feedComment;

	@Override
	public List<FeedComment> findAllByFeedId(Long feedId, Long cursorId, int pageSize) {
		return jpaQueryFactory
				.selectFrom(qFeedComment)
				.where(
						qFeedComment.feedId.eq(feedId),
						loeFeedCommentId(cursorId)
				)
				.orderBy(qFeedComment.id.asc())
				.limit(pageSize+1)
				.fetch();
	}

	private BooleanExpression loeFeedCommentId(Long feedCommentId) {
		return (feedCommentId == null) ? null : qFeedComment.id.loe(feedCommentId);
	}

}
