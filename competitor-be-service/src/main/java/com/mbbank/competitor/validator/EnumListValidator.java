package com.mbbank.competitor.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumListValidator implements ConstraintValidator<ValidType, List<String>> {
    private Class<? extends Enum<?>> enumClass;
    private String supportedValues;

    @Override
    public void initialize(ValidType constraintAnnotation) {
        this.enumClass = constraintAnnotation.in();
        this.supportedValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null || values.isEmpty()) {
            return true;
        }

        List<String> enumNames = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());

        List<String> invalidValues = values.stream()
                .filter(v -> !enumNames.contains(v))
                .collect(Collectors.toList());

        if (!invalidValues.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Invalid value(s): " + invalidValues + ". Supported values: " + supportedValues)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
