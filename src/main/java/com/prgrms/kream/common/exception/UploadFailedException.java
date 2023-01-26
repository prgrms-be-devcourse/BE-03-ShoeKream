package com.prgrms.kream.common.exception;

public class UploadFailedException extends RuntimeException {
	public UploadFailedException() {
	}

	public UploadFailedException(String message) {
		super(message);
	}
}
