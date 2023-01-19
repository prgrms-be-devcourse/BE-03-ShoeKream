package com.prgrms.kream.domain.member.model;

import static lombok.AccessLevel.*;

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Phone {
	private static final String PHONE_PATTERN = "^(010)+[0-9]{8}$";

	@Column(name = "phone", nullable = false, unique = false, length = 20)
	private String phone;

	public Phone(String phone) {
		validatePattern(phone);
		this.phone = phone;
	}

	private void validatePattern(String phone) {
		if (!Pattern.matches(PHONE_PATTERN, phone)) {
			throw new IllegalArgumentException("휴대전화번호 형식을 만족하지 않습니다.");
		}
	}
}
