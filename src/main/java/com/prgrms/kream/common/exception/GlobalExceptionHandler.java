package com.prgrms.kream.common.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		log.warn("", exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntityNotFoundException.class)
	public ErrorResponse handleEntityNotFoundException(EntityNotFoundException exception) {
		log.warn("", exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
		log.warn("", exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DuplicateRequestException.class)
	public ErrorResponse handleDuplicateRequestException(DuplicateRequestException exception) {
		log.warn("", exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(OutOfStockException.class)
	public ErrorResponse handleOutOfStockException(OutOfStockException exception) {
		log.warn("", exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DuplicateKeyException.class)
	public ErrorResponse handleDuplicateKeyException(DuplicateKeyException exception) {
		log.warn("중복 참여", exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(OptimisticLockingFailureException.class)
	public ErrorResponse optimisticLockingFailureException(OptimisticLockingFailureException exception){
		log.warn("이미 주문이 완료된 입찰입니다", exception);
		return ApiResponse.error(exception.getMessage());
	}
}
