package com.prgrms.kream.domain.bid.controller;

import org.springframework.http.HttpStatus;
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
	private final SellingBidFacade sellingBidFacade;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponse<SellingBidCreateResponse> register(
			@RequestBody SellingBidCreateRequest request) {
		SellingBidCreateResponse response = sellingBidFacade.createSellingBid(request);
		return ApiResponse.of(response);
	}

	@GetMapping("/{id}")
	public ApiResponse<SellingBidDto> findOneById(@PathVariable("id") Long id){
		SellingBidDto dto = sellingBidFacade.findOneSellingBidById(id);
		return ApiResponse.of(dto);
	}
}
