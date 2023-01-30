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

import com.prgrms.kream.domain.style.model.FeedTag;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FeedTagCustomRepositoryImpl implements FeedTagCustomRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Long> batchUpdate(List<FeedTag> feedTags) {
		return Arrays.stream(
						jdbcTemplate.batchUpdate(
								"INSERT INTO feed_tag (feed_id, tag, created_at, updated_at) VALUES (?, ?, ?, ?)",
								new BatchPreparedStatementSetter() {
									@Override
									public void setValues(PreparedStatement ps, int i) throws SQLException {
										Timestamp createdAt =
												(feedTags.get(i).getCreatedAt() == null) ?
														Timestamp.valueOf(LocalDateTime.now()) :
														Timestamp.valueOf(feedTags.get(i).getCreatedAt());
										Timestamp updatedAt =
												(feedTags.get(i).getUpdatedAt() == null) ?
														Timestamp.valueOf(LocalDateTime.now()) :
														Timestamp.valueOf(feedTags.get(i).getUpdatedAt());

										ps.setLong(1, feedTags.get(i).getFeedId());
										ps.setString(2, feedTags.get(i).getTag());
										ps.setTimestamp(3, createdAt);
										ps.setTimestamp(4, updatedAt);
									}

									@Override
									public int getBatchSize() {
										return feedTags.size();
									}
								}
						))
				.boxed()
				.map(Integer::longValue)
				.toList();
	}

}
