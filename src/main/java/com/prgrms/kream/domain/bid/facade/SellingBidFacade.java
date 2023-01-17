package com.prgrms.kream.domain.bid.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.bid.dto.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.service.SellingBidService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellingBidFacade {
	private final SellingBidService sellingBidService;

	@Transactional
	public SellingBidCreateResponse createSellingBid(SellingBidCreateRequest request){
		return sellingBidService.createSellingBid(request);
	}
}
