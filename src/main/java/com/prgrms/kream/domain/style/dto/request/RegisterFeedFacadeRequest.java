package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record RegisterFeedFacadeRequest(
		String content,
		Long authorId,
		List<MultipartFile> images
) {
}
