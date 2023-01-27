package com.prgrms.kream.domain.bid.facade;

import com.prgrms.kream.domain.bid.dto.request.BuyingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidFindResponse;
import com.prgrms.kream.domain.bid.service.BuyingBidService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyingBidFacade {
	private final BuyingBidService service;

	@Transactional
	public BuyingBidCreateResponse createBuyingBid(BuyingBidCreateRequest buyingBidCreateRequest) {
		return service.createBuyingBid(buyingBidCreateRequest);
	}

	@Transactional(readOnly = true)
	public BuyingBidFindResponse findOneBuyingBidById(Long id) {
		List<Long> ids = Collections.singletonList(id);
		BuyingBidFindRequest buyingBidFindRequest = new BuyingBidFindRequest(ids);
		return service.findOneBuyingBidById(buyingBidFindRequest);
	}

	@Transactional
	public void deleteOneBuyingBidById(Long id) {
		service.deleteOneBuyingBidById(id);
	}
}
