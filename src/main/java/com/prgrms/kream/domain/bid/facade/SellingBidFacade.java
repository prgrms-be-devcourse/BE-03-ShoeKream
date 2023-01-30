package com.prgrms.kream.domain.bid.facade;

import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.request.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.service.SellingBidService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellingBidFacade {
	private final SellingBidService service;

	@Transactional
	public SellingBidCreateResponse register(SellingBidCreateRequest request) {
		return service.register(request);
	}

	@Transactional(readOnly = true)
	public SellingBidFindResponse findById(Long id) {
		List<Long> ids = Collections.singletonList(id);
		SellingBidFindRequest request = new SellingBidFindRequest(ids);
		return service.findById(request);
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
