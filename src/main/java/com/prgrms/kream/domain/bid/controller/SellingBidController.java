package com.prgrms.kream.domain.bid.controller;

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
import com.prgrms.kream.domain.bid.dto.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.SellingBidDto;
import com.prgrms.kream.domain.bid.facade.SellingBidFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/selling_bid")
@RequiredArgsConstructor
public class SellingBidController {
	private final SellingBidFacade facade;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponse<SellingBidCreateResponse> register(
			@RequestBody SellingBidCreateRequest request) {
		SellingBidCreateResponse response = facade.createSellingBid(request);
		return ApiResponse.of(response);
	}

	@GetMapping("/{id}")
	public ApiResponse<SellingBidDto> findOne(@PathVariable("id") Long id) {
		SellingBidDto dto = facade.findOneSellingBidById(id);
		return ApiResponse.of(dto);
	}

	@DeleteMapping("/{id}")
	public ApiResponse<String> deleteOne(@PathVariable("id") Long id) {
		facade.deleteOneSellingBidById(id);
		return ApiResponse.of("판매 입찰이 삭제되었습니다");
	}
}
