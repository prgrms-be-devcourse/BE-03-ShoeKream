package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.bid.dto.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.SellingBidDto;
import com.prgrms.kream.domain.bid.model.SellingBid;

public class BidMapper {
	public static SellingBid createRequestToSellingBid(SellingBidCreateRequest request) {
		return SellingBid.builder()
				.memberId(request.memberId())
				.productOptionId(request.productOptionId())
				.price(request.price())
				.validUntil(request.validUntil())
				.build();
	}

	public static SellingBidCreateResponse sellingBidToCreateResponse(SellingBid entity) {
		return new SellingBidCreateResponse(entity.getId());
	}

	public static SellingBidDto SellingBidToDto(SellingBid entity){
		return new SellingBidDto(
				entity.getId(),
				entity.getMemberId(),
				entity.getProductOptionId(),
				entity.getPrice(),
				entity.getValidUntil()
		);
	}
}
