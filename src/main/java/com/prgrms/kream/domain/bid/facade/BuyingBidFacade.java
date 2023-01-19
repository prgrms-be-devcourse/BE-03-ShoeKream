package com.prgrms.kream.domain.bid.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.bid.dto.request.BuyingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidCreateResponse;
import com.prgrms.kream.domain.bid.service.BuyingBidService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyingBidFacade {
	private final BuyingBidService service;

	@Transactional
	public BuyingBidCreateResponse createBuyingBid(BuyingBidCreateRequest buyingBidCreateRequest){
		return service.createBuyinBid(buyingBidCreateRequest);
	}
}
