package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.bid.dto.request.BuyingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.request.SellingBidCreateRequest;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.BuyingBidFindResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidCreateResponse;
import com.prgrms.kream.domain.bid.dto.response.SellingBidFindResponse;
import com.prgrms.kream.domain.bid.model.BuyingBid;
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

	public static SellingBidCreateResponse toSellingBidCreateResponse(SellingBid sellingBid) {
		return new SellingBidCreateResponse(sellingBid.getId());
	}

	public static SellingBidFindResponse toSellingBidFindResponse(SellingBid sellingBid) {
		return new SellingBidFindResponse(
				sellingBid.getId(),
				sellingBid.getMemberId(),
				sellingBid.getProductOptionId(),
				sellingBid.getPrice(),
				sellingBid.getValidUntil()
		);
	}

	public static BuyingBid toBuyingBid(BuyingBidCreateRequest buyingBidCreateRequest) {
		return BuyingBid.builder()
				.id(buyingBidCreateRequest.id())
				.memberId(buyingBidCreateRequest.memberId())
				.productOptionId(buyingBidCreateRequest.productOptionId())
				.price(buyingBidCreateRequest.price())
				.validUntil(buyingBidCreateRequest.validUntil())
				.build();
	}

	public static BuyingBidCreateResponse toBuyingBidCreateResponse(BuyingBid buyingBid) {
		return new BuyingBidCreateResponse(buyingBid.getId());
	}

	public static BuyingBidFindResponse toBuyingBidFindResponse(BuyingBid buyingBid) {
		return new BuyingBidFindResponse(
				buyingBid.getId(),
				buyingBid.getMemberId(),
				buyingBid.getProductOptionId(),
				buyingBid.getPrice(),
				buyingBid.getValidUntil()
		);
	}
}
