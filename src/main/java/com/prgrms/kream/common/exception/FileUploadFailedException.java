package com.prgrms.kream.common.exception;

public class FileUploadFailedException extends RuntimeException {
	public FileUploadFailedException() {
	}

	public FileUploadFailedException(String message) {
		super(message);
	}
}
