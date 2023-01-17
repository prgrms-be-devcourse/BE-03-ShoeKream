package com.prgrms.kream.domain.bid.facade;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.bid.dto.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.SellingBidDto;
import com.prgrms.kream.domain.bid.dto.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.service.SellingBidService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellingBidFacade {
	private final SellingBidService service;

	@Transactional
	public SellingBidCreateResponse createSellingBid(SellingBidCreateRequest request){
		return service.createSellingBid(request);
	}

	@Transactional(readOnly = true)
	public SellingBidDto findOneSellingBidById(Long id){
		List<Long> ids = Collections.singletonList(id);
		SellingBidFindRequest request = new SellingBidFindRequest(ids);
		return service.findOneSellingBidById(request);
	}

	@Transactional
	public void deleteOneSellingBidById(Long id){
		service.deleteOneSellingBidById(id);
	}
}
