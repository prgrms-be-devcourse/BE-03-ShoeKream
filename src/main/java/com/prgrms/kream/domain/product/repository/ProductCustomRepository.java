package com.prgrms.kream.domain.product.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.prgrms.kream.domain.product.model.Product;

public interface ProductCustomRepository {
	Slice<Product> findAllByCursor(Long cursorId, Pageable pageable);
}
