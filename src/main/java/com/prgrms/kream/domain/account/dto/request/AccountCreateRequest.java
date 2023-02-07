package com.prgrms.kream.domain.account.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

@ApiModel("계좌 생성 요청")
public record AccountCreateRequest(
		@ApiModelProperty(value = "계좌 id", required = true, example = "1")
		@NotNull(message = "계좌의 ID는 필수 입력 값입니다")
		Long id,

		@ApiModelProperty(value = "계좌 주인 id", required = true, example = "2")
		@NotNull(message = "계좌 주인의 ID는 필수 입력 값입니다")
		Long memberId
) {
}
