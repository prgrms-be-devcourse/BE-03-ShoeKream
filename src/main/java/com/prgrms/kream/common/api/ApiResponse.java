package com.prgrms.kream.common.api;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
	private final T data;

	private ApiResponse(T data) {
		this.data = data;
	}

	public static <T> ApiResponse<T> of(T data) {
		return new ApiResponse<>(data);
	}

	public static ErrorResponse error(String message) {
		return new ErrorResponse(message);
	}

	@Getter
		private final String error;

		private ErrorResponse(String error) {
			this.error = error;
		}
	}
}