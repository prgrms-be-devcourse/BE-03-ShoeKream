package com.prgrms.kream.domain.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prgrms.kream.domain.product.model.Product;
import com.prgrms.kream.domain.product.model.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long>, ProductOptionCustomRepository {

	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM ProductOption productOption WHERE productOption.product.id = :productId")
	void deleteAllByProductId(Long productId);

	List<ProductOption> findAllByProduct(Product product);
}
