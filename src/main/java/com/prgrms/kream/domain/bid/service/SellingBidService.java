package com.prgrms.kream.domain.bid.service;

import static com.prgrms.kream.common.mapper.BidMapper.*;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.common.mapper.BidMapper;
import com.prgrms.kream.domain.bid.dto.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.SellingBidDto;
import com.prgrms.kream.domain.bid.dto.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.repository.SellingBidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellingBidService {
	private final SellingBidRepository repository;

	@Transactional
	public SellingBidCreateResponse createSellingBid(SellingBidCreateRequest request) {
		return sellingBidToCreateResponse(repository.save(createRequestToSellingBid(request)));
	}

	@Transactional(readOnly = true)
	public SellingBidDto findOneSellingBidById(SellingBidFindRequest request) {
		return repository.findById(request.ids().get(0))
				.map(BidMapper::SellingBidToDto)
				.orElseThrow(EntityNotFoundException::new);
	}
}
