package com.prgrms.kream.domain.image.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.prgrms.kream.domain.image.model.DomainType;

public interface ImageService {

	void register(List<MultipartFile> multipartFiles, Long referenceId, DomainType domainType);
}
