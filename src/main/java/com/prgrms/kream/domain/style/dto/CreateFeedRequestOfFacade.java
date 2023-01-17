package com.prgrms.kream.domain.style.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public record CreateFeedRequestOfFacade(
		String content,
		Long author,
		List<MultipartFile> images
) {
}
