package com.prgrms.kream.domain.product.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.prgrms.kream.domain.product.validator.ShoeSize;

public record ProductRegisterRequest(
		@NotBlank(message = "상품 이름은 필수 입력사항입니다.")
		@Size(max = 30, message = "상품 이름은 {max}글자 이하로 입력할 수 있습니다.")
		String name,

		@NotNull(message = "출시 가격은 필수 입력사항입니다.")
		@Positive(message = "출시 가격은 0 또는 음수일 수 없습니다.")
		int releasePrice,

		@NotBlank(message = "상품 설명을 입력해주세요.")
		String description,

		List<@ShoeSize(message = "잘못된 형식의 신발 사이즈가 입력되었습니다.") Integer> sizes,

		List<MultipartFile> images) {
}
