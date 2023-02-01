package com.prgrms.kream.domain.style.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record FeedCommentRegisterRequest(
		@NotEmpty(message = "댓글은 공백일 수 없습니다.")
		String content,

		@NotNull(message = "작성자 식별값은 비어있을 수 없습니다.")
		@Positive(message = "작성자 식별값이 올바르지 않습니다.")
		Long memberId
) {
}
