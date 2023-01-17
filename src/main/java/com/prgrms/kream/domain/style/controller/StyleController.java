package com.prgrms.kream.domain.style.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.domain.style.dto.CreateFeedRequestOfFacade;
import com.prgrms.kream.domain.style.dto.FeedResponse;
import com.prgrms.kream.domain.style.dto.UpdateFeedRequestOfFacade;
import com.prgrms.kream.domain.style.facade.StyleFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feed")
public class StyleController {

	private final StyleFacade styleFacade;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public ApiResponse<FeedResponse> register(@ModelAttribute CreateFeedRequestOfFacade request) {
		return ApiResponse.of(
				styleFacade.register(request)
		);
	}

	@PutMapping("/{id}")
	public ApiResponse<FeedResponse> update(@PathVariable long id, @RequestBody UpdateFeedRequestOfFacade request) {
		return ApiResponse.of(
				styleFacade.update(id, request)
		);
	}

	@DeleteMapping("/{id}")
	public ApiResponse<String> delete(@PathVariable long id) {
		styleFacade.delete(id);
		return ApiResponse.of(
				"성공적으로 삭제 됐습니다."
		);
	}

}
