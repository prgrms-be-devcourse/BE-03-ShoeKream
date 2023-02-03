package com.prgrms.kream.domain.account.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("계좌 조회 요청")
public record AccountGetRequest(
		@ApiModelProperty(value = "계좌 주인 id", required = true, example ="1")
		Long memberId
) {
}
