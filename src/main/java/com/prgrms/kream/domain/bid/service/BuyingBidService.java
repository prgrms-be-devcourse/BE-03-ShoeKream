package com.prgrms.kream.domain.bid.service;

import static com.prgrms.kream.common.mapper.BidMapper.toBuyingBid;
import static com.prgrms.kream.common.mapper.BidMapper.toBuyingBidCreateResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.bid.dto.request.BuyingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidCreateResponse;
import com.prgrms.kream.domain.bid.repository.BuyingBidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyingBidService {
	private final BuyingBidRepository repository;

	@Transactional
	public BuyingBidCreateResponse createBuyinBid(BuyingBidCreateRequest buyingBidCreateRequest) {
		return toBuyingBidCreateResponse(repository.save(toBuyingBid(buyingBidCreateRequest)));
	}
}
