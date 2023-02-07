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
	public SellingBidCreateResponse registerSellingBid(SellingBidCreateRequest request) {
		return toSellingBidCreateResponse(repository.save(toSellingBid(request)));
	}

	@Transactional(readOnly = true)
	public SellingBidFindResponse getSellingBid(SellingBidFindRequest request) {
		return repository.findById(request.ids().get(0))
				.map(BidMapper::toSellingBidFindResponse)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 판매 입찰입니다"));
	}

	@Transactional
	public void deleteSellingBid(Long id) {
		SellingBid sellingBid =
				repository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재 하지 않는 판매 입찰을 삭제할 수 없습니다."));
		sellingBid.delete();
	}

	@Transactional
	public void restoreSellingBid(Long id) {
		SellingBid sellingBid =
				repository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재 하지 않는 판매 입찰을 복구할 수 없습니다."));
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
