package com.prgrms.kream.domain.product.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prgrms.kream.domain.product.model.ProductOption;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductOptionCustomRepositoryImpl implements ProductOptionCustomRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Long> saveAllBulk(List<ProductOption> productOptions) {
		return Arrays.stream(
						jdbcTemplate.batchUpdate(
								"""
										INSERT INTO product_option 
										(`size`, product_id, highest_price, lowest_price, created_at, updated_at)
										VALUES (?,?,?,?,?,?)
										""",
								new BatchPreparedStatementSetter() {
									@Override
									public void setValues(PreparedStatement preparedStatement, int index) throws SQLException {

										LocalDateTime createdAt = productOptions.get(index).getCreatedAt();
										if (createdAt == null) {
											createdAt = LocalDateTime.now();
										}

										preparedStatement.setInt(1, productOptions.get(index).getSize());
										preparedStatement.setLong(2, productOptions.get(index).getProduct().getId());
										preparedStatement.setInt(3, 0);
										preparedStatement.setInt(4, 0);
										preparedStatement.setObject(5, createdAt);
										preparedStatement.setObject(6, LocalDateTime.now());
									}

									@Override
									public int getBatchSize() {
										return productOptions.size();
									}
								}
						))
				.boxed()
				.map(Long::valueOf)
				.toList();
	}
}
