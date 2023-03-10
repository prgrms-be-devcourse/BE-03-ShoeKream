package com.prgrms.kream.domain.product.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.model.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	QProduct qProduct = QProduct.product;

	@Override
	public List<Product> findAllByCursor(Long cursorId, int pageSize, String searchWord) {

		return jpaQueryFactory
				.selectFrom(qProduct)
				.where(
						ltProductId(cursorId),
						qProduct.name.like(searchWord + "%")
				)
				.orderBy(qProduct.id.desc())
				.limit(pageSize)
				.fetch();
	}

	private BooleanExpression ltProductId(Long cursorId) {
		if (cursorId == null) {
			return null;
		}

		return qProduct.id.lt(cursorId);
	}
}
