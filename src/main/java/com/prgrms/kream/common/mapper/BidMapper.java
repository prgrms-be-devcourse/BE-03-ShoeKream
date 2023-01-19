package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.model.SellingBid;

public class BidMapper {
	public static SellingBid toSellingBid(SellingBidCreateRequest sellingBidCreateRequest) {
		return SellingBid.builder()
				.id(sellingBidCreateRequest.id())
				.memberId(sellingBidCreateRequest.memberId())
				.productOptionId(sellingBidCreateRequest.productOptionId())
				.price(sellingBidCreateRequest.price())
				.validUntil(sellingBidCreateRequest.validUntil())
				.build();
	}

	public static SellingBidCreateResponse toCreateResponse(SellingBid sellingBid) {
		return new SellingBidCreateResponse(sellingBid.getId());
	}

	public static SellingBidFindResponse toSellingBidFindResponse(SellingBid sellingBid){
		return new SellingBidFindResponse(
				sellingBid.getId(),
				sellingBid.getMemberId(),
				sellingBid.getProductOptionId(),
				sellingBid.getPrice(),
				sellingBid.getValidUntil()
		);
	}
}
