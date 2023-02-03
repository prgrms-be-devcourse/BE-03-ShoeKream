package com.prgrms.kream.domain.bid.service;

import static com.prgrms.kream.common.mapper.BidMapper.*;
import com.prgrms.kream.common.mapper.BidMapper;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.request.BuyingBidFindRequest;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidFindResponse;
import com.prgrms.kream.domain.bid.model.BuyingBid;
import com.prgrms.kream.domain.bid.repository.BuyingBidRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyingBidService {
	private final BuyingBidRepository repository;

	@Transactional
	public BuyingBidCreateResponse registerBuyingBid(BuyingBidCreateRequest buyingBidCreateRequest) {
		return toBuyingBidCreateResponse(repository.save(toBuyingBid(buyingBidCreateRequest)));
	}

	@Transactional(readOnly = true)
	public BuyingBidFindResponse getBuyingBid(BuyingBidFindRequest buyingBidFindRequest) {
		return repository.findById(buyingBidFindRequest.ids().get(0))
				.map(BidMapper::toBuyingBidFindResponse)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 구매 입찰입니다"));
	}

	@Transactional
	public void deleteBuyingBid(Long id) {
		BuyingBid buyingBid =
				repository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재 하지 않는 구매 입찰을 삭제할 수 없습니다."));
		buyingBid.delete();
	}

	@Transactional
	public void restoreBuyingBid(Long id) {
		BuyingBid buyingBid =
				repository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재 하지 않는 구매 입찰을 복구할 수 없습니다."));
		if (buyingBid.getValidUntil().isAfter(LocalDateTime.now())) {
			buyingBid.restore();
		}
	}

	@Transactional(readOnly = true)
	public BuyingBidFindResponse findHighestBuyingBidByProductOptionId(BuyingBidFindRequest buyingBidFindRequest) {
		Optional<BuyingBid> buyingBid = repository.findHighestBuyingBidByProductOptionId(buyingBidFindRequest.ids().get(0));
		return buyingBid.map(BidMapper::toBuyingBidFindResponse)
				.orElseThrow(EntityNotFoundException::new);
	}
}
