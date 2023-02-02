package com.prgrms.kream.domain.style.controller;

import org.springframework.core.convert.converter.Converter;

import com.prgrms.kream.domain.style.dto.request.SortType;

public class SortTypeConverter implements Converter<String, SortType> {

	@Override
	public SortType convert(String value) {
		return SortType.of(value);
	}

}
