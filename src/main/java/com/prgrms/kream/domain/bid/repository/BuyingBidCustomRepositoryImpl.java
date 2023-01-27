package com.prgrms.kream.domain.bid.repository;

import com.prgrms.kream.domain.bid.model.BuyingBid;
import com.prgrms.kream.domain.bid.model.QBuyingBid;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BuyingBidCustomRepositoryImpl implements BuyingBidCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	QBuyingBid qBuyingBid = QBuyingBid.buyingBid;

	@Override
	public List<BuyingBid> findHighestBuyingBidByProductOptionId(Long productOptionId) {
		return jpaQueryFactory
				.selectFrom(qBuyingBid)
				.where(qBuyingBid.productOptionId.eq(productOptionId))
				.orderBy(qBuyingBid.price.desc())
				.orderBy(qBuyingBid.createdAt.asc())
				.limit(1)
				.fetch();
	}
}
