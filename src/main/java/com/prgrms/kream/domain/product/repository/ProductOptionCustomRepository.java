package com.prgrms.kream.domain.product.repository;

import java.util.List;

import com.prgrms.kream.domain.product.model.ProductOption;

public interface ProductOptionCustomRepository {

	List<Long> saveAllBulk(List<ProductOption> productOptions);
}
