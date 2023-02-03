package com.prgrms.kream.domain.account.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;

@ApiModel("계좌 내역 조회 요청")
public record TransactionHistoryGetFacadeRequest(
		@ApiModelProperty(value = "조회하고자 하는 회원 id", required = true, example = "1")
		@NotNull(message = "거래 내역을 조회하고 사는 당사자의 Id는 필수로 있어야 합니다")
		Long memberId
) {
}