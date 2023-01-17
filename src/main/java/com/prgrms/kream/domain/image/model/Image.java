package com.prgrms.kream.domain.image.model;

import static lombok.AccessLevel.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.prgrms.kream.common.model.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "IMAGE")
@NoArgsConstructor(access = PROTECTED)
public class Image extends BaseTimeEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "original_name", nullable = false)
	private String originalName;

	@Column(name = "full_path", nullable = false)
	private String fullPath;

	@Column(name = "reference_id", nullable = false)
	private Long referenceId;

	@Enumerated(EnumType.STRING)
	@Column(name = "domain_type", nullable = false)
	private DomainType domainType;

	@Builder
	public Image(String originalName, String fullPath, Long referenceId, DomainType domainType) {
		this.originalName = originalName;
		this.fullPath = fullPath;
		this.referenceId = referenceId;
		this.domainType = domainType;
	}
}
