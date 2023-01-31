package com.prgrms.kream.domain.bid.service;

import static com.prgrms.kream.common.mapper.BidMapper.*;
import com.prgrms.kream.common.mapper.BidMapper;
import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.request.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.model.SellingBid;
import com.prgrms.kream.domain.bid.repository.SellingBidRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellingBidService {
	private final SellingBidRepository repository;

	@Transactional
	public SellingBidCreateResponse register(SellingBidCreateRequest request) {
		return toSellingBidCreateResponse(repository.save(toSellingBid(request)));
	}

	@Transactional(readOnly = true)
	public SellingBidFindResponse findById(SellingBidFindRequest request) {
		return repository.findById(request.ids().get(0))
				.map(BidMapper::toSellingBidFindResponse)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Transactional
	public void deleteById(Long id) {
		SellingBid sellingBid = repository.findById(id).orElseThrow(EntityNotFoundException::new);
		sellingBid.delete();
	}

	@Transactional
	public void restoreById(Long id) {
		SellingBid sellingBid = repository.findById(id).orElseThrow(EntityNotFoundException::new);
		if (sellingBid.getValidUntil().isAfter(LocalDateTime.now())) {
			sellingBid.restore();
		}
	}

	@Transactional(readOnly = true)
	public SellingBidFindResponse findLowestSellingBidByProductOptionId(SellingBidFindRequest sellingBidFindRequest) {
		Optional<SellingBid> sellingBids =
				repository.findLowestSellingBidByProductOptionId(sellingBidFindRequest.ids().get(0));
		return sellingBids.map(BidMapper::toSellingBidFindResponse)
				.orElseThrow(EntityNotFoundException::new);
	}
}
