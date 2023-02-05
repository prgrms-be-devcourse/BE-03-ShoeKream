package com.prgrms.kream.domain.style.dto.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("수정할 피드 정보")
public record FeedUpdateRequest(
		@NotEmpty(message = "피드 본문이 비어있을 수 없습니다.")
		@Length(max = 255, message = "최대 글자수({max})를 초과했습니다.")
		@ApiModelProperty(value = "본문", required = true, example = "피드 본문에 들어갈 내용")
		String content,

		@ApiModelProperty(value = "상품 식별자", required = false)
		List<Long> productIds
) {
}
