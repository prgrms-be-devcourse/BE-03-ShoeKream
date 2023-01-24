package com.prgrms.kream.domain.product.repository;

import java.util.List;

import com.prgrms.kream.domain.product.model.Product;

public interface ProductCustomRepository {
	List<Product> findAllByCursor(Long cursorId, int pageSize);
}
