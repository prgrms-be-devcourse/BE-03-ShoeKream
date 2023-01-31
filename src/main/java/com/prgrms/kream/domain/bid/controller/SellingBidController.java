package com.prgrms.kream.domain.bid.controller;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.facade.SellingBidFacade;
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
@RequestMapping("/api/v1/selling-bid")
@RequiredArgsConstructor
public class SellingBidController {
	private final SellingBidFacade facade;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponse<SellingBidCreateResponse> register(
			@RequestBody @Valid SellingBidCreateRequest request) {
		return ApiResponse.of(facade.register(request));
	}

	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse<SellingBidFindResponse> findOne(@PathVariable("id") Long id) {
		return ApiResponse.of(facade.findById(id));
	}

	@PutMapping("/delete/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse<String> delete(@PathVariable("id") Long id) {
		facade.deleteById(id);
		return ApiResponse.of("판매 입찰이 삭제되었습니다");
	}

	@PutMapping("/restore/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse<String> restore(@PathVariable("id") Long id) {
		facade.restoreById(id);
		return ApiResponse.of("판매 입찰이 복구되었습니다");
	}
}
