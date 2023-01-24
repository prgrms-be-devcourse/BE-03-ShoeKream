package com.prgrms.kream.common.mapper;

import com.prgrms.kream.domain.style.dto.request.LikeFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.LikeFeedRequest;
import com.prgrms.kream.domain.style.dto.request.LikeFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedServiceRequest;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedResponse;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedServiceResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedServiceResponse;
import com.prgrms.kream.domain.style.model.Feed;
import com.prgrms.kream.domain.style.model.FeedLike;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StyleMapper {

	public static RegisterFeedFacadeRequest toRegisterFeedFacadeRequest(RegisterFeedRequest registerFeedRequest
	) {
		return new RegisterFeedFacadeRequest(
				registerFeedRequest.content(),
				registerFeedRequest.authorId(),
				registerFeedRequest.images()
		);
	}

	public static RegisterFeedServiceRequest toRegisterFeedServiceRequest(
			RegisterFeedFacadeRequest registerFeedFacadeRequest) {
		return new RegisterFeedServiceRequest(
				registerFeedFacadeRequest.content(),
				registerFeedFacadeRequest.authorId()
		);
	}

	public static Feed toFeed(RegisterFeedServiceRequest registerFeedServiceRequest) {
		return Feed.builder()
				.content(registerFeedServiceRequest.content())
				.authorId(registerFeedServiceRequest.authorId())
				.build();
	}

	public static RegisterFeedServiceResponse toRegisterFeedServiceResponse(Feed feed) {
		return new RegisterFeedServiceResponse(feed.getId());
	}

	public static RegisterFeedFacadeResponse toRegisterFeedFacadeResponse(
			RegisterFeedServiceResponse registerFeedServiceResponse) {
		return new RegisterFeedFacadeResponse(registerFeedServiceResponse.id());
	}

	public static RegisterFeedResponse toRegisterFeedResponse(RegisterFeedFacadeResponse registerFeedFacadeResponse) {
		return new RegisterFeedResponse(registerFeedFacadeResponse.id());
	}

	public static UpdateFeedFacadeRequest toUpdateFeedFacadeRequest(UpdateFeedRequest updateFeedRequest) {
		return new UpdateFeedFacadeRequest(updateFeedRequest.content());
	}

	public static UpdateFeedServiceRequest toUpdateFeedServiceRequest(UpdateFeedFacadeRequest updateFeedFacadeRequest) {
		return new UpdateFeedServiceRequest(updateFeedFacadeRequest.content());
	}

	public static UpdateFeedServiceResponse toUpdateFeedServiceResponse(Feed feed) {
		return new UpdateFeedServiceResponse(feed.getId());
	}

	public static UpdateFeedFacadeResponse toUpdateFeedFacadeResponse(UpdateFeedServiceResponse updateFeedServiceResponse) {
		return new UpdateFeedFacadeResponse(updateFeedServiceResponse.id());
	}

	public static UpdateFeedResponse toUpdateFeedResponse(UpdateFeedFacadeResponse updateFeedFacadeResponse) {
		return new UpdateFeedResponse(updateFeedFacadeResponse.id());
	}

	public static LikeFeedFacadeRequest toLikeFeedFacadeRequest(long id, LikeFeedRequest likeFeedRequest) {
		return new LikeFeedFacadeRequest(id, likeFeedRequest.memberId());
	}

	public static LikeFeedServiceRequest toLikeFeedServiceRequest(LikeFeedFacadeRequest likeFeedFacadeRequest) {
		return new LikeFeedServiceRequest(
				likeFeedFacadeRequest.feedId(),
				likeFeedFacadeRequest.memberId());
	}

	public static FeedLike toFeedLike(LikeFeedServiceRequest likeFeedServiceRequest) {
		return FeedLike.builder()
				.feedId(likeFeedServiceRequest.feedId())
				.memberId(likeFeedServiceRequest.memberId())
				.build();
	}

}
