package com.prgrms.kream.domain.bid.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.facade.SellingBidFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/selling-bid")
@RequiredArgsConstructor
public class SellingBidController {
	private final SellingBidFacade facade;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponse<SellingBidCreateResponse> register(
			@RequestBody @Valid SellingBidCreateRequest request) {
		return ApiResponse.of(facade.createSellingBid(request));
	}

	@GetMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse<SellingBidFindResponse> findOne(@PathVariable("id") Long id) {
		return ApiResponse.of(facade.findOneSellingBidById(id));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public ApiResponse<String> deleteOne(@PathVariable("id") Long id) {
		facade.deleteOneSellingBidById(id);
		return ApiResponse.of("판매 입찰이 삭제되었습니다");
	}
}
