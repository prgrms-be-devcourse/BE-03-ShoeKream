package com.prgrms.kream.domain.bid.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.bid.dto.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.facade.SellingBidFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/selling_bid")
@RequiredArgsConstructor
public class SellingBidController {
	private final SellingBidFacade sellingBidFacade;

	@PostMapping
	public ApiResponse<SellingBidCreateResponse> register(
			@RequestBody SellingBidCreateRequest request) {
		return ApiResponse.of(sellingBidFacade.createSellingBid(request));
	}
}
