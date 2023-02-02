package com.prgrms.kream.domain.style.dto.request;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SortType {

	POPULAR,
	NEWEST;

	@JsonCreator
	public static SortType of(String value) {
		if (value == null) {
			return POPULAR;
		}

		return Arrays.stream(SortType.values())
				.filter(sortType -> sortType.name().equals(value.toUpperCase()))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("일치하는 정렬 조건이 없습니다."));
	}

}
