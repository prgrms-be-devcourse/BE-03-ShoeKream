package com.prgrms.kream.domain.bid.service;

import static com.prgrms.kream.common.mapper.BidMapper.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.bid.dto.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.repository.SellingBidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellingBidService {
	private final SellingBidRepository repository;

	@Transactional
	public SellingBidCreateResponse createSellingBid(SellingBidCreateRequest request){
		return sellingBidToCreateResponse(repository.save(createRequestToSellingBid(request)));
	}
}
