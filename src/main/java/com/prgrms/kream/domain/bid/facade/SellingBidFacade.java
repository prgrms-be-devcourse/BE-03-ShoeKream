package com.prgrms.kream.domain.bid.facade;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.dto.request.SellingBidFindRequest;
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
	public SellingBidFindResponse findOneSellingBidById(Long id){
		List<Long> ids = Collections.singletonList(id);
		SellingBidFindRequest request = new SellingBidFindRequest(ids);
		return service.findOneSellingBidById(request);
	}

	@Transactional
	public void deleteOneSellingBidById(Long id){
		service.deleteOneSellingBidById(id);
	}

	@Transactional(readOnly = true)
	public SellingBidFindResponse findLowestSellingBid(Long productId){
		SellingBidFindRequest sellingBidFindRequest = new SellingBidFindRequest(Collections.singletonList(productId));
		return service.findLowestSellingBidByProductOptionId(sellingBidFindRequest);
	}
}
