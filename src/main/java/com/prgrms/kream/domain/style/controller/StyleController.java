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
import com.prgrms.kream.domain.style.dto.request.GetFeedCommentRequest;
import com.prgrms.kream.domain.style.dto.request.GetFeedRequest;
import com.prgrms.kream.domain.style.dto.request.LikeFeedRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedCommentRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedRequest;
import com.prgrms.kream.domain.style.dto.response.GetFeedCommentResponses;
import com.prgrms.kream.domain.style.dto.response.GetFeedResponses;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedResponse;
import com.prgrms.kream.domain.style.facade.StyleFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
public class StyleController {

	private final String SUCCESS_MESSAGE = "성공적으로 작업이 완료 됐습니다.";

	private final StyleFacade styleFacade;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<RegisterFeedResponse> register(
			@ModelAttribute @Valid RegisterFeedRequest registerFeedRequest
	) {
		return ApiResponse.of(
				toRegisterFeedResponse(
						styleFacade.register(
								toRegisterFeedFacadeRequest(registerFeedRequest)
						)
				)
		);
	}

	@GetMapping("/trending")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<GetFeedResponses> getTrendingFeeds(@Valid GetFeedRequest getFeedRequest) {
		return ApiResponse.of(
				toGetFeedResponses(
						styleFacade.getTrendingFeeds(
								toGetFeedFacadeRequest(getFeedRequest)
						)
				)
		);
	}

	@GetMapping("/newest")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<GetFeedResponses> getNewestFeeds(@Valid GetFeedRequest getFeedRequest) {
		return ApiResponse.of(
				toGetFeedResponses(
						styleFacade.getNewestFeeds(
								toGetFeedFacadeRequest(getFeedRequest)
						)
				)
		);
	}

	@GetMapping("/tags/{tag}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<GetFeedResponses> getAllByTag(@PathVariable String tag, @Valid GetFeedRequest getFeedRequest) {
		return ApiResponse.of(
				toGetFeedResponses(
						styleFacade.getAllByTag(
								toGetFeedFacadeRequest(getFeedRequest),
								tag
						)
				)
		);
	}

	@GetMapping("/members/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<GetFeedResponses> getAllByMember(@PathVariable Long id, @Valid GetFeedRequest getFeedRequest) {
		return ApiResponse.of(
				toGetFeedResponses(
						styleFacade.getAllByMember(
								toGetFeedFacadeRequest(getFeedRequest),
								id
						)
				)
		);
	}

	@GetMapping("/products/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<GetFeedResponses> getAllByProduct(@PathVariable Long id, @Valid GetFeedRequest getFeedRequest) {
		return ApiResponse.of(
				toGetFeedResponses(
						styleFacade.getAllByProduct(
								toGetFeedFacadeRequest(getFeedRequest),
								id
						)
				)
		);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<UpdateFeedResponse> update(
			@PathVariable long id,
			@RequestBody @Valid UpdateFeedRequest updateFeedRequest) {
		return ApiResponse.of(
				toUpdateFeedResponse(
						styleFacade.update(
								id,
								toUpdateFeedFacadeRequest(updateFeedRequest)
						)
				)
		);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<String> delete(@PathVariable Long id) {
		styleFacade.delete(id);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	@PostMapping("/{id}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> registerFeedLike(
			@PathVariable Long id,
			@RequestBody @Valid LikeFeedRequest likeFeedRequest) {
		styleFacade.registerFeedLike(
				toLikeFeedFacadeRequest(
						id,
						likeFeedRequest
				)
		);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	@DeleteMapping("/{id}/like")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<String> deleteFeedLike(
			@PathVariable Long id,
			@RequestBody @Valid LikeFeedRequest likeFeedRequest) {
		styleFacade.deleteFeedLike(
				toLikeFeedFacadeRequest(
						id,
						likeFeedRequest
				)
		);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	@PostMapping("/{id}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> registerFeedComment(
			@PathVariable Long id,
			@RequestBody @Valid RegisterFeedCommentRequest registerFeedCommentRequest) {
		styleFacade.registerFeedComment(
				toRegisterFeedCommentFacadeRequest(
						id,
						registerFeedCommentRequest
				)
		);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	@GetMapping("/{id}/comments")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<GetFeedCommentResponses> getAllFeedComments(
			@PathVariable Long id,
			@Valid GetFeedCommentRequest getFeedCommentRequest) {
		return ApiResponse.of(
				toGetFeedCommentResponses(
						styleFacade.getAllFeedComments(
								toGetFeedCommentFacadeRequest(
										id,
										getFeedCommentRequest
								)
						)
				)
		);
	}

}
