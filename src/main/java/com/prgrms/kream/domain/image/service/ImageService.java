package com.prgrms.kream.domain.image.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.prgrms.kream.common.exception.FileUploadFailedException;
import com.prgrms.kream.domain.image.model.DomainType;

public interface ImageService {

	void registerImage(List<MultipartFile> multipartFiles, Long referenceId, DomainType domainType);

	List<String> getAllImages(Long referenceId, DomainType domainType);

	void deleteAllImagesByReference(Long referenceId, DomainType domainType);

	default String createUniqueName(String originalName) {
		String extension = extractExtension(originalName);
		String uuid = UUID.randomUUID().toString();

		return uuid + "-" + LocalDate.now() + extension;
	}

	default String extractExtension(String originalName) {
		try {
			return originalName.substring(originalName.lastIndexOf("."));
		} catch (StringIndexOutOfBoundsException e) {
			throw new FileUploadFailedException("올바르지 않은 파일 형식입니다.");
		}
	}
}
