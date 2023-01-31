package com.prgrms.kream.domain.bid.repository;

import com.prgrms.kream.domain.bid.model.QSellingBid;
import com.prgrms.kream.domain.bid.model.SellingBid;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SellingBidCustomRepositoryImpl implements SellingBidCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;

	QSellingBid qSellingBid = QSellingBid.sellingBid;

	// todo 현재 fetch() -> fetchFirst() 로 수정하기 . NPE 대처 방법 팔요
	@Override
	public Optional<SellingBid> findLowestSellingBidByProductOptionId(Long productOptionId) {
		List<SellingBid> sellingBids = jpaQueryFactory
				.selectFrom(qSellingBid)
				.where(qSellingBid.productOptionId.eq(productOptionId))
				.where(qSellingBid.isDeleted.isFalse())
				.orderBy(qSellingBid.price.asc())
				.orderBy(qSellingBid.createdAt.asc())
				.limit(1)
				.fetch();
		if (sellingBids.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(sellingBids.get(0));
	}
}
