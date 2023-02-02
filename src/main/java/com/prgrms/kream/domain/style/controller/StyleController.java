package com.prgrms.kream.domain.style.controller;

import static com.prgrms.kream.common.mapper.StyleMapper.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.style.dto.request.FeedCommentGetRequest;
import com.prgrms.kream.domain.style.dto.request.FeedGetRequest;
import com.prgrms.kream.domain.style.dto.request.FeedLikeRequest;
import com.prgrms.kream.domain.style.dto.request.FeedCommentRegisterRequest;
import com.prgrms.kream.domain.style.dto.request.FeedRegisterRequest;
import com.prgrms.kream.domain.style.dto.request.FeedUpdateRequest;
import com.prgrms.kream.domain.style.dto.response.FeedCommentGetResponses;
import com.prgrms.kream.domain.style.dto.response.FeedGetResponses;
import com.prgrms.kream.domain.style.dto.response.FeedRegisterResponse;
import com.prgrms.kream.domain.style.dto.response.FeedUpdateResponse;
import com.prgrms.kream.domain.style.facade.StyleFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feeds")
public class StyleController {

	private final String SUCCESS_MESSAGE = "성공적으로 작업이 완료 됐습니다.";

	private final StyleFacade styleFacade;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<FeedRegisterResponse> registerFeed(
			@ModelAttribute @Valid FeedRegisterRequest feedRegisterRequest
	) {
		return ApiResponse.of(
				toFeedRegisterResponse(
						styleFacade.registerFeed(
								toFeedRegisterFacadeRequest(feedRegisterRequest)
						)
				)
		);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<FeedGetResponses> getAllFeeds(@Valid FeedGetRequest feedGetRequest) {
		return ApiResponse.of(
				toFeedGetResponses(
						styleFacade.getAllFeeds(
								toFeedGetFacadeRequest(feedGetRequest)
						)
				)
		);
	}

	@GetMapping("/tags/{tag}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<FeedGetResponses> getAllFeedsByTag(@PathVariable String tag, @Valid FeedGetRequest feedGetRequest) {
		return ApiResponse.of(
				toFeedGetResponses(
						styleFacade.getAllFeedsByTag(
								toFeedGetFacadeRequest(feedGetRequest),
								tag
						)
				)
		);
	}

	@GetMapping("/members/{memberId}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<FeedGetResponses> getAllFeedsByMemberId(@PathVariable Long memberId, @Valid FeedGetRequest feedGetRequest) {
		return ApiResponse.of(
				toFeedGetResponses(
						styleFacade.getAllFeedsByMemberId(
								toFeedGetFacadeRequest(feedGetRequest),
								memberId
						)
				)
		);
	}

	@GetMapping("/products/{productId}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<FeedGetResponses> getAllFeedsByProductId(@PathVariable Long productId, @Valid FeedGetRequest feedGetRequest) {
		return ApiResponse.of(
				toFeedGetResponses(
						styleFacade.getAllFeedsByProductId(
								toFeedGetFacadeRequest(feedGetRequest),
								productId
						)
				)
		);
	}

	@PutMapping("/{feedId}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<FeedUpdateResponse> updateFeed(
			@PathVariable long feedId,
			@RequestBody @Valid FeedUpdateRequest feedUpdateRequest) {
		return ApiResponse.of(
				toFeedUpdateResponse(
						styleFacade.updateFeed(
								feedId,
								toFeedUpdateFacadeRequest(feedUpdateRequest)
						)
				)
		);
	}

	@DeleteMapping("/{feedId}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<String> deleteFeed(@PathVariable Long feedId) {
		styleFacade.deleteFeed(feedId);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	@PostMapping("/{feedId}/likes")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> registerFeedLike(
			@PathVariable Long feedId,
			@RequestBody @Valid FeedLikeRequest feedLikeRequest) {
		styleFacade.registerFeedLike(
				toFeedLikeFacadeRequest(
						feedId,
						feedLikeRequest
				)
		);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	@DeleteMapping("/{feedId}/likes")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<String> deleteFeedLike(
			@PathVariable Long feedId,
			@RequestBody @Valid FeedLikeRequest feedLikeRequest) {
		styleFacade.deleteFeedLike(
				toFeedLikeFacadeRequest(
						feedId,
						feedLikeRequest
				)
		);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	@PostMapping("/{feedId}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> registerFeedComment(
			@PathVariable Long feedId,
			@RequestBody @Valid FeedCommentRegisterRequest feedCommentRegisterRequest) {
		styleFacade.registerFeedComment(
				toFeedCommentRegisterFacadeRequest(
						feedId,
						feedCommentRegisterRequest
				)
		);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	@GetMapping("/{feedId}/comments")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<FeedCommentGetResponses> getAllFeedComments(
			@PathVariable Long feedId,
			@Valid FeedCommentGetRequest feedCommentGetRequest) {
		return ApiResponse.of(
				toFeedCommentGetResponses(
						styleFacade.getAllFeedComments(
								toFeedCommentGetFacadeRequest(
										feedId,
										feedCommentGetRequest
								)
						)
				)
		);
	}

}
