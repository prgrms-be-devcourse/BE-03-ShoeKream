package com.prgrms.kream.domain.member.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record MemberUpdateFacadeRequest(
		Long id,
		String name,
		String phone,
		String password,
		MultipartFile imageFile
) {
}
