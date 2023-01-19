package com.prgrms.kream.domain.product.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
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
	public Slice<Product> findAllByCursor(Long cursorId, Pageable pageable) {

		List<Product> products = jpaQueryFactory
				.selectFrom(qProduct)
				.where(ltBProductId(cursorId))
				.orderBy(qProduct.id.desc())
				.limit(pageable.getPageSize() + 1)
				.fetch();

		boolean hasNext = false;

		if (products.size() == pageable.getPageSize() + 1) {
			products.remove(pageable.getPageSize());
			hasNext = true;
		}

		return new SliceImpl<>(products, pageable, hasNext);
	}

	private BooleanExpression ltBProductId(Long cursorId) {
		if (cursorId == null) {
			return null;
		}

		return qProduct.id.lt(cursorId);
	}
}
