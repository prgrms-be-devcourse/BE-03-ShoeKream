package com.prgrms.kream.domain.bid.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidFindResponse;
import com.prgrms.kream.domain.bid.facade.BuyingBidFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/selling-bid")
@RequiredArgsConstructor
public class BuyingBidController {
	private final BuyingBidFacade facade;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponse<BuyingBidCreateResponse> register(
			@RequestBody @Valid BuyingBidCreateRequest buyingBidCreateRequest) {
		return ApiResponse.of(facade.createBuyingBid(buyingBidCreateRequest));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.FOUND)
	public ApiResponse<BuyingBidFindResponse> findOne(@PathVariable("id") Long id) {
		return ApiResponse.of(facade.findOneBuyingBidById(id));
	}
}
