package com.prgrms.kream.domain.bid.service;

import static com.prgrms.kream.common.mapper.BidMapper.toBuyingBid;
import static com.prgrms.kream.common.mapper.BidMapper.toBuyingBidCreateResponse;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.common.mapper.BidMapper;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidFindResponse;
import com.prgrms.kream.domain.bid.repository.BuyingBidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuyingBidService {
	private final BuyingBidRepository repository;

	@Transactional
	public BuyingBidCreateResponse createBuyingBid(BuyingBidCreateRequest buyingBidCreateRequest) {
		return toBuyingBidCreateResponse(repository.save(toBuyingBid(buyingBidCreateRequest)));
	}

	@Transactional(readOnly = true)
	public BuyingBidFindResponse findOneBuyingBidById(BuyingBidFindRequest buyingBidFindRequest) {
		return repository.findById(buyingBidFindRequest.ids().get(0))
				.map(BidMapper::toBuyingBidFindResponse)
				.orElseThrow(EntityNotFoundException::new);
	}
}
