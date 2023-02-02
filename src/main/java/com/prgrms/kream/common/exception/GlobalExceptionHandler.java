package com.prgrms.kream.common.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prgrms.kream.common.api.ApiResponse;
import com.prgrms.kream.common.api.ApiResponse.ErrorResponse;
import com.sun.jdi.request.DuplicateRequestException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ErrorResponse handleEntityNotFoundException(EntityNotFoundException exception) {
		log.info("exception : " + exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ErrorResponse handleCustomRuntimeException(BindException exception) {
		log.info("exception : " + exception);
		return ApiResponse.error(exception.getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({
			DuplicatedEmailException.class, FileUploadFailedException.class,
			OptimisticLockingFailureException.class, FileDeleteFailedException.class,
			OutOfStockException.class, DuplicateRequestException.class, IllegalArgumentException.class
	})
	public ErrorResponse handleCustomRuntimeException(RuntimeException exception) {
		log.info("exception : " + exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RuntimeException.class)
	public ErrorResponse handleRuntimeException(RuntimeException exception) {
		log.error("exception : " + exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponse handleException(Exception exception) {
		log.error("exception : " + exception);
		return ApiResponse.error(exception.getMessage());
	}
}
