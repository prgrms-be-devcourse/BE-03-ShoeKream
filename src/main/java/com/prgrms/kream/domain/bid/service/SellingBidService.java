package com.prgrms.kream.domain.bid.service;

import static com.prgrms.kream.common.mapper.BidMapper.*;
import com.prgrms.kream.common.mapper.BidMapper;
import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.request.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.model.SellingBid;
import com.prgrms.kream.domain.bid.repository.SellingBidRepository;
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
	public SellingBidCreateResponse createSellingBid(SellingBidCreateRequest request) {
		return toSellingBidCreateResponse(repository.save(toSellingBid(request)));
	}

	@Transactional(readOnly = true)
	public SellingBidFindResponse findOneSellingBidById(SellingBidFindRequest request) {
		return repository.findById(request.ids().get(0))
				.map(BidMapper::toSellingBidFindResponse)
				.orElseThrow(EntityNotFoundException::new);
	}

	@Transactional
	public void deleteOneSellingBidById(Long id) {
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public SellingBidFindResponse findLowestSellingBidByProductOptionId(SellingBidFindRequest sellingBidFindRequest) {
		Optional<SellingBid> sellingBids =
				repository.findLowestSellingBidByProductOptionId(sellingBidFindRequest.ids().get(0));
		return sellingBids.map(BidMapper::toSellingBidFindResponse)
				.orElseThrow(EntityNotFoundException::new);
	}
}
