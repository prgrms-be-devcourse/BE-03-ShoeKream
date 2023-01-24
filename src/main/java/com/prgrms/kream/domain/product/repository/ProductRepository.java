package com.prgrms.kream.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {
}
