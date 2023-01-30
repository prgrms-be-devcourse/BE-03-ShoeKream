package com.prgrms.kream.domain.style.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prgrms.kream.domain.style.model.FeedProduct;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FeedProductCustomRepositoryImpl implements FeedProductCustomRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Long> batchUpdate(List<FeedProduct> feedProducts) {
		return Arrays.stream(
						jdbcTemplate.batchUpdate(
								"INSERT INTO feed_product (feed_id, product_id, created_at, updated_at) VALUES (?, ?, ?, ?)",
								new BatchPreparedStatementSetter() {
									@Override
									public void setValues(PreparedStatement ps, int i) throws SQLException {
										Timestamp createdAt =
												(feedProducts.get(i).getCreatedAt() == null) ?
														Timestamp.valueOf(LocalDateTime.now()) :
														Timestamp.valueOf(feedProducts.get(i).getCreatedAt());
										Timestamp updatedAt =
												(feedProducts.get(i).getUpdatedAt() == null) ?
														Timestamp.valueOf(LocalDateTime.now()) :
														Timestamp.valueOf(feedProducts.get(i).getUpdatedAt());

										ps.setLong(1, feedProducts.get(i).getFeedId());
										ps.setLong(2, feedProducts.get(i).getProductId());
										ps.setTimestamp(3, createdAt);
										ps.setTimestamp(4, updatedAt);
									}

									@Override
									public int getBatchSize() {
										return feedProducts.size();
									}
								}
						))
				.boxed()
				.map(Integer::longValue)
				.toList();
	}

}
