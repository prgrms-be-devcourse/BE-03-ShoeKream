package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record FeedRegisterFacadeRequest(
		String content,
		Long authorId,
		List<MultipartFile> images,
		List<Long> productIds
) {
}
