package com.prgrms.kream.common.exception;

public class OutOfStockException extends RuntimeException {

	private final String message;

	public OutOfStockException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
