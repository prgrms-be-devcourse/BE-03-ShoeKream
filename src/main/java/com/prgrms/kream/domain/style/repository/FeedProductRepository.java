package com.prgrms.kream.domain.style.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.kream.domain.style.model.FeedProduct;

public interface FeedProductRepository extends JpaRepository<FeedProduct, Long> {
}
