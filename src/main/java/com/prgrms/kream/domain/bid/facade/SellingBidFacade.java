package com.prgrms.kream.domain.bid.facade;

import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.request.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.service.SellingBidService;
import com.prgrms.kream.domain.product.service.ProductService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellingBidFacade {
	private final SellingBidService sellingBidService;

	private final ProductService productService;

	@Transactional
	public SellingBidCreateResponse register(SellingBidCreateRequest request) {
		productService.compareLowestPrice(request.productOptionId(), request.price());
		return sellingBidService.register(request);
	}

	@Transactional(readOnly = true)
	public SellingBidFindResponse findById(Long id) {
		List<Long> ids = Collections.singletonList(id);
		SellingBidFindRequest request = new SellingBidFindRequest(ids);
		return sellingBidService.findById(request);
	}

	@Transactional
	public void deleteById(Long id) {
		sellingBidService.deleteById(id);
	}

	@Transactional
	public void restoreById(Long id) {
		sellingBidService.restoreById(id);
	}
}
