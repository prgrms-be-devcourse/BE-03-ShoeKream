package com.prgrms.kream.domain.product.controller.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public record ProductRegisterRequest(
		@NotBlank(message = "상품 이름은 필수 입력사항입니다.")
		@Size(max = 30, message = "상품 이름은 {max}글자 이하로 입력할 수 있습니다.")
		String name,

		@NotNull(message = "출시 가격은 필수 입력사항입니다.")
		@Positive(message = "출시 가격은 0 또는 음수일 수 없습니다.")
		int releasePrice,

		@NotBlank(message = "상품 설명을 입력해주세요.")
		String description,

		List<MultipartFile> images) {
}
