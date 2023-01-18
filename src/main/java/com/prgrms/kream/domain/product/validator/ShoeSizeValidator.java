package com.prgrms.kream.domain.product.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ShoeSizeValidator implements ConstraintValidator<ShoeSize, Integer> {

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return isValidSizeRange(value) && isValidSizeUnits(value);
	}

	private boolean isValidSizeRange(int value) {
		return value > 0 && value <= 400;
	}

	private boolean isValidSizeUnits(int value) {
		return value % 5 == 0;
	}
}
