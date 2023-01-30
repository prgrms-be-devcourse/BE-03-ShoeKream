package com.prgrms.kream.domain.image.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long>, ImageCustomRepository {

	List<Image> findAllByReferenceIdAndDomainType(Long referenceId, DomainType domainType);

	@Modifying
	@Query("DELETE FROM Image image WHERE image.referenceId = :referenceId AND image.domainType = :domainType")
	void deleteAllByReferenceIdAndDomainType(Long referenceId, DomainType domainType);
}
