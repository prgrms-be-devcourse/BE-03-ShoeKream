package com.prgrms.kream.common.exception;

public class DuplicatedEmailException extends RuntimeException {

	private final String message;

	public DuplicatedEmailException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
