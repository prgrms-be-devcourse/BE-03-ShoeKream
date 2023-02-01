package com.prgrms.kream.domain.account.controller;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.account.dto.response.AccountCreateResponse;
import com.prgrms.kream.domain.account.facade.AccountFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
	private final AccountFacade accountFacade;

	@PostMapping("/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponse<AccountCreateResponse> register(@PathVariable("id") Long id) {
		return ApiResponse.of(accountFacade.register(id));
	}
}
