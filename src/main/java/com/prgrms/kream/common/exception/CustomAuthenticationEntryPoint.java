package com.prgrms.kream.common.exception;

import static javax.servlet.http.HttpServletResponse.*;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.kream.common.api.ApiResponse;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		ApiResponse.ErrorResponse errorResponse = ApiResponse.error("로그인 후 사용가능합니다.");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(SC_UNAUTHORIZED);

		OutputStream outputStream = response.getOutputStream();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(outputStream, errorResponse);
		outputStream.flush();
	}
}
