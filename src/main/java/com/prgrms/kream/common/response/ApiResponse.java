package com.prgrms.kream.common.response;

public class ApiResponse<T> {
	private T data;

	private ApiResponse(T data) {
		this.data = data;
	}

	public static <T> ApiResponse<T> of(T data) {
		return new ApiResponse<>(data);
	}

	public static ErrorResponse error(String message) {
		return new ErrorResponse(message);
	}

	static final class ErrorResponse {
		private final String error;

		private ErrorResponse(String error) {
			this.error = error;
		}
	}
}
