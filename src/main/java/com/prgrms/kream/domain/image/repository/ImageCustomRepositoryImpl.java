package com.prgrms.kream.domain.image.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prgrms.kream.domain.image.model.Image;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImageCustomRepositoryImpl implements ImageCustomRepository {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public List<Long> saveAllBulk(List<Image> images) {
		return Arrays.stream(
						jdbcTemplate.batchUpdate(
								"""
										INSERT INTO image 
										(original_name, full_path, reference_id, domain_type, created_at, updated_at)
										VALUES (?,?,?,?,?,?)
										""",
								new BatchPreparedStatementSetter() {
									@Override
									public void setValues(PreparedStatement preparedStatement, int index) throws SQLException {

										LocalDateTime createdAt = images.get(index).getCreatedAt();
										if (createdAt == null) {
											createdAt = LocalDateTime.now();
										}

										preparedStatement.setString(1, images.get(index).getOriginalName());
										preparedStatement.setString(2, images.get(index).getFullPath());
										preparedStatement.setLong(3, images.get(index).getReferenceId());
										preparedStatement.setString(4, images.get(index).getDomainType().name());
										preparedStatement.setObject(5, createdAt);
										preparedStatement.setObject(6, LocalDateTime.now());
									}

									@Override
									public int getBatchSize() {
										return images.size();
									}
								}
						))
				.boxed()
				.map(Long::valueOf)
				.toList();
	}
}
