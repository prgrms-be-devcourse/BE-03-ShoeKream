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
import com.prgrms.kream.domain.style.dto.request.GetFeedRequest;
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

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ApiResponse<GetFeedResponses> get(GetFeedRequest getFeedRequest) {
		return ApiResponse.of(
				toGetFeedResponses(
						styleFacade.get(
								toGetFeedFacadeRequest(getFeedRequest)
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
	public void delete(@PathVariable long id) {
		styleFacade.delete(id);
	}

	@PostMapping("/{id}/like")
	@ResponseStatus(HttpStatus.CREATED)
	public void registerFeedLike(
			@PathVariable long id,
			@RequestBody @Valid LikeFeedRequest likeFeedRequest) {
		styleFacade.registerFeedLike(
				toLikeFeedFacadeRequest(
						id,
						likeFeedRequest
				)
		);
	}

	@DeleteMapping("/{id}/like")
	@ResponseStatus(HttpStatus.OK)
	public void deleteFeedLike(
			@PathVariable long id,
			@RequestBody @Valid LikeFeedRequest likeFeedRequest) {
		styleFacade.deleteFeedLike(
				toLikeFeedFacadeRequest(
						id,
						likeFeedRequest
				)
		);
	}
}
