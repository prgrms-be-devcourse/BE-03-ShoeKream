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
	public BuyingBidCreateResponse register(BuyingBidCreateRequest buyingBidCreateRequest) {
		return service.register(buyingBidCreateRequest);
	}

	@Transactional(readOnly = true)
	public BuyingBidFindResponse findById(Long id) {
		List<Long> ids = Collections.singletonList(id);
		BuyingBidFindRequest buyingBidFindRequest = new BuyingBidFindRequest(ids);
		return service.findById(buyingBidFindRequest);
	}

	@Transactional
	public void deleteById(Long id) {
		service.deleteById(id);
	}

	@Transactional
	public void restoreById(Long id) {
		service.restoreById(id);
	}
}
