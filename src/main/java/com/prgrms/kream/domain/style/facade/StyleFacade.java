package com.prgrms.kream.domain.style.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.kream.domain.image.model.DomainType;
import com.prgrms.kream.domain.image.service.ImageService;
import com.prgrms.kream.domain.member.service.MemberService;
import com.prgrms.kream.domain.style.dto.CreateFeedRequestOfFacade;
import com.prgrms.kream.domain.style.dto.CreateFeedRequestOfService;
import com.prgrms.kream.domain.style.dto.FeedResponse;
import com.prgrms.kream.domain.style.dto.UpdateFeedRequestOfFacade;
import com.prgrms.kream.domain.style.dto.UpdateFeedRequestOfService;
import com.prgrms.kream.domain.style.service.StyleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StyleFacade {

	private final StyleService styleService;

	private final MemberService memberService;

	private final ImageService imageService;

	@Transactional
	public FeedResponse register(CreateFeedRequestOfFacade request) {
		FeedResponse response = styleService.register(
				CreateFeedRequestOfService.builder()
						.content(request.content())
						.author(memberService.getMember(request.author()))
						.build());

		imageService.register(request.images(), response.id(), DomainType.FEED);
		return response;
	}

	@Transactional
	public FeedResponse update(long id, UpdateFeedRequestOfFacade request) {
		return styleService.update(
				id,
				UpdateFeedRequestOfService.builder()
						.content(request.content())
						.build());
	}

	@Transactional
	public void delete(long id) {
		styleService.delete(id);
	}

}
