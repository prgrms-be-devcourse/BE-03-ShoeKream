package com.prgrms.kream.common.exception;

import java.util.NoSuchElementException;

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
		log.warn("잘못된 입력", exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public ErrorResponse handleNoSuchElementException(NoSuchElementException exception) {
		log.warn("존재하지 않습니다.", exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
		log.warn("잘못된 요청", exception);
		return ApiResponse.error(exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DuplicateRequestException.class)
	public ErrorResponse handleDuplicateRequestException(DuplicateRequestException exception) {
		log.warn("중복 참여", exception);
		return ApiResponse.error(exception.getMessage());
	}
}
