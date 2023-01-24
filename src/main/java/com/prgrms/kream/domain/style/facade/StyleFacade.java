package com.prgrms.kream.domain.style.facade;

import static com.prgrms.kream.common.mapper.StyleMapper.*;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.service.ImageService;
import com.prgrms.kream.domain.member.service.MemberService;
import com.prgrms.kream.domain.style.dto.request.LikeFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.RegisterFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.request.UpdateFeedFacadeRequest;
import com.prgrms.kream.domain.style.dto.response.RegisterFeedFacadeResponse;
import com.prgrms.kream.domain.style.dto.response.UpdateFeedFacadeResponse;
import com.prgrms.kream.domain.style.service.StyleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StyleFacade {

	private final StyleService styleService;

	private final MemberService memberService;

	private final ImageService imageService;

	@Transactional
	public RegisterFeedFacadeResponse register(RegisterFeedFacadeRequest registerFeedFacadeRequest) {
		RegisterFeedFacadeResponse registerFeedFacadeResponse = toRegisterFeedFacadeResponse(
				styleService.register(
						toRegisterFeedServiceRequest(registerFeedFacadeRequest)
				)
		);

		// 이미지 등록 서비스 호출
		imageService.register(
				registerFeedFacadeRequest.images(),
				registerFeedFacadeResponse.id(),
				DomainType.FEED
		);

		return registerFeedFacadeResponse;
	}

	@Transactional
	public UpdateFeedFacadeResponse update(long id, UpdateFeedFacadeRequest updateFeedFacadeRequest) {
		return toUpdateFeedFacadeResponse(
				styleService.update(
						id,
						toUpdateFeedServiceRequest(updateFeedFacadeRequest)
				)
		);
	}

	@Transactional
	public void delete(long id) {
		styleService.delete(id);
	}

	@Transactional
	public void registerFeedLike(LikeFeedFacadeRequest likeFeedFacadeRequest) {
		styleService.registerFeedLike(
				toLikeFeedServiceRequest(likeFeedFacadeRequest)
		);
	}

	@Transactional
	public void deleteFeedLike(LikeFeedFacadeRequest likeFeedFacadeRequest) {
		styleService.deleteFeedLike(
				toLikeFeedServiceRequest(likeFeedFacadeRequest)
		);
	}

}
