package com.prgrms.kream.domain.account.controller;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.account.dto.request.AccountUpdateFacadeRequest;
import com.prgrms.kream.domain.account.dto.request.TransactionHistoryGetFacadeRequest;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.dto.response.AccountUpdateResponse;
import com.prgrms.kream.domain.account.dto.response.TransactionHistoryGetResponse;
import com.prgrms.kream.domain.account.facade.AccountFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Api(tags = "계좌 컨트롤러")
public class AccountController {
	private final AccountFacade accountFacade;

	@PostMapping("/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	@ApiOperation(value = "계좌 생성")
	public ApiResponse<AccountCreateResponse> registerAccount(
			@PathVariable("id")
			@ApiParam(value = "계좌를 만들고자 하는 회원 id", required = true, example = "1")
			Long id) {
		return ApiResponse.of(accountFacade.registerAccount(id));
	}

	@PutMapping
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "계좌 입출금")
	public ApiResponse<AccountUpdateResponse> updateBalance(
			@RequestBody @Valid
			@ApiParam(value = "거래 정보", required = true)
			AccountUpdateFacadeRequest accountUpdateFacadeRequest
	) {
		return ApiResponse.of(accountFacade.updateBalance(accountUpdateFacadeRequest));
	}

	@GetMapping("transaction-histories/{memberId}")
	@ResponseStatus(code = HttpStatus.OK)
	@ApiOperation(value = "거래 내역 조회")
	public ApiResponse<List<TransactionHistoryGetResponse>> getAll(
			@PathVariable("memberId")
			@ApiParam(value = "거래 내역을 조회하고자 하는 회원 id", required = true, example = "1")
			Long memberId) {
		TransactionHistoryGetFacadeRequest transactionHistoryGetFacadeRequest =
				new TransactionHistoryGetFacadeRequest(memberId);
		return ApiResponse.of(accountFacade.getAllTransactionHistories(transactionHistoryGetFacadeRequest));
	}
}
