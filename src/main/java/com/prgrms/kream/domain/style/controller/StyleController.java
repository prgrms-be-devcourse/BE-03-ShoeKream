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
import com.prgrms.kream.domain.style.dto.request.LikeFeedRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedRequest;
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

	@GetMapping("/newest")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<GetFeedResponses> getNewestFeeds() {
		return ApiResponse.of(
				toGetFeedResponses(
						styleFacade.getNewestFeeds()
				)
		);
	}

	@GetMapping("/tags/{tag}")
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<GetFeedResponses> getAllByTag(@PathVariable String tag) {
		return ApiResponse.of(
				toGetFeedResponses(
						styleFacade.getAllByTag(tag)
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
	public ApiResponse<String> delete(@PathVariable long id) {
		styleFacade.delete(id);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	@PostMapping("/{id}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public ApiResponse<String> registerFeedLike(
			@PathVariable long id,
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
			@PathVariable long id,
			@RequestBody @Valid LikeFeedRequest likeFeedRequest) {
		styleFacade.deleteFeedLike(
				toLikeFeedFacadeRequest(
						id,
						likeFeedRequest
				)
		);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}
}
