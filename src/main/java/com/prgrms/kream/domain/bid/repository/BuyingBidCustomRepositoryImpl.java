package com.prgrms.kream.domain.bid.repository;

import com.prgrms.kream.domain.bid.model.BuyingBid;
import com.prgrms.kream.domain.bid.model.QBuyingBid;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BuyingBidCustomRepositoryImpl implements BuyingBidCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	QBuyingBid qBuyingBid = QBuyingBid.buyingBid;

	// todo 현재 fetch() -> fetchFirst() 로 수정하기 . NPE 대처 방법 팔요
	@Override
	public Optional<BuyingBid> findHighestBuyingBidByProductOptionId(Long productOptionId) {
		List<BuyingBid> buyingBids = jpaQueryFactory
				.selectFrom(qBuyingBid)
				.where(qBuyingBid.productOptionId.eq(productOptionId))
				.where(qBuyingBid.isDeleted.isFalse())
				.orderBy(qBuyingBid.price.desc())
				.orderBy(qBuyingBid.createdAt.asc())
				.limit(1)
				.fetch();
		if (buyingBids.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(buyingBids.get(0));
	}
}
