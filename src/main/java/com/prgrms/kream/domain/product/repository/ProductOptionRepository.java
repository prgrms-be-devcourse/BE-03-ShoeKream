package com.prgrms.kream.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.product.model.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
