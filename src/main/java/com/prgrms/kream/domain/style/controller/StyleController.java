package com.prgrms.kream.domain.style.controller;

import static com.prgrms.kream.common.mapper.StyleMapper.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/feeds")
@Api(tags = "스타일(커뮤니티) 컨트롤러")
public class StyleController {

	private static final String SUCCESS_MESSAGE = "성공적으로 작업이 완료 됐습니다.";

	private final StyleFacade styleFacade;

	/**
	 * 사용자가 피드를 등록한다.
	 * @author Kim-Changgyu
	 * @param feedRegisterRequest 본문, 작성자 식별자, 피드 이미지 리스트, 상품 식별자 리스트
	 * @return ApiResponse<FeedRegisterResponses>
	 * @see StyleFacade
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "피드 등록", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "content", value = "본문", dataType = "string", paramType = "form", required = true),
			@ApiImplicitParam(name = "authorId", value = "작성자 식별자", dataType = "long", paramType = "form", required = true),
			@ApiImplicitParam(name = "images", value = "피드 이미지 리스트", dataType = "multipart-file", allowMultiple = true, paramType = "form", required = false),
			@ApiImplicitParam(name = "productIds", value = "상품 식별자 리스트", dataType = "long", allowMultiple = true, paramType = "form", required = false)
	})
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

	/**
	 * 사용자가 전체 피드를 조회한다.
	 * @author Kim-Changgyu
	 * @param feedGetRequest 커서 식별자, 페이지 크기, 정렬 조건
	 * @return ApiResponse<FeedGetResponses>
	 * @see StyleFacade
	 */
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "피드 조회 (전체)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cursorId", value = "커서 식별자", dataType = "long", paramType = "query", required = false),
			@ApiImplicitParam(name = "pageSize", value = "페이지 크기", dataType = "int", paramType = "query", required = false),
			@ApiImplicitParam(name = "sortType", value = "정렬 조건 (popular, newest)", dataType = "string", paramType = "query", required = false)
	})
	public ApiResponse<FeedGetResponses> getAllFeeds(
			@Valid FeedGetRequest feedGetRequest
	) {
		return ApiResponse.of(
				toFeedGetResponses(
						styleFacade.getAllFeeds(
								toFeedGetFacadeRequest(feedGetRequest)
						)
				)
		);
	}

	/**
	 * 사용자가 태그 기준으로 피드를 조회한다.
	 * @author Kim-Changgyu
	 * @param tag 태그 키워드
	 * @param feedGetRequest 커서 식별자, 페이지 크기, 정렬 조건
	 * @return ApiResponse<FeedGetResponses>
	 * @see StyleFacade
	 */
	@GetMapping("/tags/{tag}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "피드 조회 (태그 기준)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "tag", value = "태그", dataType = "string", paramType = "path", required = true),
			@ApiImplicitParam(name = "cursorId", value = "커서 식별자", dataType = "long", paramType = "query", required = false),
			@ApiImplicitParam(name = "pageSize", value = "페이지 크기", dataType = "int", paramType = "query", required = false),
			@ApiImplicitParam(name = "sortType", value = "정렬 조건 (popular, newest)", dataType = "string", paramType = "query", required = false)
	})
	public ApiResponse<FeedGetResponses> getAllFeedsByTag(@PathVariable String tag,
			@Valid FeedGetRequest feedGetRequest) {
		return ApiResponse.of(
				toFeedGetResponses(
						styleFacade.getAllFeedsByTag(
								toFeedGetFacadeRequest(feedGetRequest),
								tag
						)
				)
		);
	}

	/**
	 * 사용자가 사용자 기준으로 피드를 조회한다.
	 * @author Kim-Changgyu
	 * @param memberId 사용자 식별자
	 * @param feedGetRequest 커서 식별자, 페이지 크기, 정렬 조건
	 * @return ApiResponse<FeedGetResponses>
	 * @see StyleFacade
	 */
	@GetMapping("/members/{memberId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "피드 조회 (사용자 기준)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberId", value = "사용자 식별자", dataType = "long", paramType = "path", required = true),
			@ApiImplicitParam(name = "cursorId", value = "커서 식별자", dataType = "long", paramType = "query", required = false),
			@ApiImplicitParam(name = "pageSize", value = "페이지 크기", dataType = "int", paramType = "query", required = false),
			@ApiImplicitParam(name = "sortType", value = "정렬 조건 (popular, newest)", dataType = "string", paramType = "query", required = false)
	})
	public ApiResponse<FeedGetResponses> getAllFeedsByMemberId(@PathVariable Long memberId,
			@Valid FeedGetRequest feedGetRequest) {
		return ApiResponse.of(
				toFeedGetResponses(
						styleFacade.getAllFeedsByMemberId(
								toFeedGetFacadeRequest(feedGetRequest),
								memberId
						)
				)
		);
	}

	/**
	 * 사용자가 사용자 기준으로 피드를 조회한다.
	 * @author Kim-Changgyu
	 * @param productId 상품 식별자
	 * @param feedGetRequest 커서 식별자, 페이지 크기, 정렬 조건
	 * @return ApiResponse<FeedGetResponses>
	 * @see StyleFacade
	 */
	@GetMapping("/products/{productId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "피드 조회 (사용자 기준)")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "productId", value = "상품 식별자", dataType = "long", paramType = "path", required = true),
			@ApiImplicitParam(name = "cursorId", value = "커서 식별자", dataType = "long", paramType = "query", required = false),
			@ApiImplicitParam(name = "pageSize", value = "페이지 크기", dataType = "int", paramType = "query", required = false),
			@ApiImplicitParam(name = "sortType", value = "정렬 조건 (popular, newest)", dataType = "string", paramType = "query", required = false)
	})
	public ApiResponse<FeedGetResponses> getAllFeedsByProductId(@PathVariable Long productId,
			@Valid FeedGetRequest feedGetRequest) {
		return ApiResponse.of(
				toFeedGetResponses(
						styleFacade.getAllFeedsByProductId(
								toFeedGetFacadeRequest(feedGetRequest),
								productId
						)
				)
		);
	}

	/**
	 * 사용자가 피드를 수정한다.
	 * @author Kim-Changgyu
	 * @param feedId 피드 식별자
	 * @param feedUpdateRequest 본문, 상품 식별자 리스트
	 * @return ApiResponse<FeedUpdateResponse>
	 * @see StyleFacade
	 */
	@PutMapping("/{feedId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "피드 수정")
	public ApiResponse<FeedUpdateResponse> updateFeed(
			@ApiParam(value = "수정 대상 피드 식별자", required = true)
			@PathVariable long feedId,
			@ApiParam(value = "수정할 피드 요청 정보", required = true)
			@RequestBody @Valid FeedUpdateRequest feedUpdateRequest
	) {
		return ApiResponse.of(
				toFeedUpdateResponse(
						styleFacade.updateFeed(
								feedId,
								toFeedUpdateFacadeRequest(feedUpdateRequest)
						)
				)
		);
	}

	/**
	 * 사용자가 피드를 삭제한다.
	 * @author Kim-Changgyu
	 * @param feedId 피드 식별자
	 * @return ApiResponse<String>
	 * @see StyleFacade
	 */
	@DeleteMapping("/{feedId}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "피드 삭제")
	public ApiResponse<String> deleteFeed(
			@ApiParam(value = "삭제 대상 피드 식별자", required = true)
			@PathVariable Long feedId
	) {
		styleFacade.deleteFeed(feedId);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	/**
	 * 사용자가 특정 피드에 좋아요를 등록한다.
	 * @author Kim-Changgyu
	 * @param feedId 피드 식별자
	 * @param feedLikeRequest 사용자 식별자
	 * @return ApiResponse<String>
	 * @see StyleFacade
	 */
	@PostMapping("/{feedId}/likes")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "피드 좋아요 등록")
	public ApiResponse<String> registerFeedLike(
			@ApiParam(value = "피드 식별자", required = true)
			@PathVariable Long feedId,
			@ApiParam(value = "좋아요 등록 요청 정보", required = true)
			@RequestBody @Valid FeedLikeRequest feedLikeRequest) {
		styleFacade.registerFeedLike(
				toFeedLikeFacadeRequest(
						feedId,
						feedLikeRequest
				)
		);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	/**
	 * 사용자가 특정 피드에 좋아요를 삭제한다.
	 * @author Kim-Changgyu
	 * @param feedId 피드 식별자
	 * @param feedLikeRequest 사용자 식별자
	 * @return ApiResponse<String>
	 * @see StyleFacade
	 */
	@DeleteMapping("/{feedId}/likes")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "피드 좋아요 삭제")
	public ApiResponse<String> deleteFeedLike(
			@ApiParam(value = "피드 식별자", required = true)
			@PathVariable Long feedId,
			@ApiParam(value = "좋아요 삭제 요청 정보", required = true)
			@RequestBody @Valid FeedLikeRequest feedLikeRequest) {
		styleFacade.deleteFeedLike(
				toFeedLikeFacadeRequest(
						feedId,
						feedLikeRequest
				)
		);
		return ApiResponse.of(SUCCESS_MESSAGE);
	}

	/**
	 * 사용자가 특정 피드에 댓글을 등록한다.
	 * @author Kim-Changgyu
	 * @param feedId 피드 식별자
	 * @param feedCommentRegisterRequest 본문, 사용자 식별자
	 * @return ApiResponse<String>
	 * @see StyleFacade
	 */
	@PostMapping("/{feedId}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "피드 댓글 등록")
	public ApiResponse<String> registerFeedComment(
			@ApiParam(value = "피드 식별자", required = true)
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

	/**
	 * 사용자가 특정 피드에 댓글을 등록한다.
	 * @author Kim-Changgyu
	 * @param feedId 피드 식별자
	 * @param feedCommentGetRequest 커서 식별자, 페이지 크기, 정렬 조건
	 * @return ApiResponse<FeedCommentGetResponses>
	 * @see StyleFacade
	 */
	@GetMapping("/{feedId}/comments")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "피드 댓글 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cursorId", value = "커서 식별자", dataType = "long", paramType = "query", required = false),
			@ApiImplicitParam(name = "pageSize", value = "페이지 크기", dataType = "int", paramType = "query", required = false),
			@ApiImplicitParam(name = "sortType", value = "정렬 조건 (popular, newest)", dataType = "string", paramType = "query", required = false)
	})
	public ApiResponse<FeedCommentGetResponses> getAllFeedComments(
			@ApiParam(value = "피드 식별자", required = true)
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
