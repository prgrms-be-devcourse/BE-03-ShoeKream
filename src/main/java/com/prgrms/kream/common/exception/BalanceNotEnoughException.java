package com.prgrms.kream.common.exception;

public class BalanceNotEnoughException extends RuntimeException {

	private final String message;

	public BalanceNotEnoughException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
