package com.prgrms.kream.domain.bid.service;

import static com.prgrms.kream.common.mapper.BidMapper.*;

import com.prgrms.kream.domain.bid.model.SellingBid;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.common.mapper.BidMapper;
import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.dto.request.SellingBidFindRequest;
import com.prgrms.kream.domain.bid.repository.SellingBidRepository;

import lombok.RequiredArgsConstructor;

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
	public void deleteOneSellingBidById(Long id){
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public SellingBidFindResponse findLowestSellingBidByProductOptionId(SellingBidFindRequest sellingBidFindRequest){
		List<SellingBid> sellingBids = repository.findLowestSellingBidByProductOptionId(sellingBidFindRequest.ids().get(0));
		if (sellingBids.isEmpty()){
			return new SellingBidFindResponse(-1L, -1L, -1L, 999999, LocalDateTime.now());
		}
		return toSellingBidFindResponse(sellingBids.get(0));
	}
}
