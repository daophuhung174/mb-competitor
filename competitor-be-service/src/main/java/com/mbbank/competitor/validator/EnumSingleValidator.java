package com.mbbank.competitor.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class EnumSingleValidator implements ConstraintValidator<ValidType, String> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ValidType annotation) {
        acceptedValues = Arrays.stream(annotation.in().getEnumConstants())
                .map(Enum::name)
                .toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || acceptedValues.contains(value);
    }
}
