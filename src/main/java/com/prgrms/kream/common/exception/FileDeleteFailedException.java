package com.prgrms.kream.common.exception;

public class FileDeleteFailedException extends RuntimeException {
	public FileDeleteFailedException() {
	}

	public FileDeleteFailedException(String message) {
		super(message);
	}
}
