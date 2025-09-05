package com.mbbank.competitor.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;


public class MinValueValidator implements ConstraintValidator<MinValue, List<Integer>> {

    private int minValue;

    @Override
    public void initialize(MinValue constraintAnnotation) {
        this.minValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(List<Integer> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (Integer element : value) {
            if (element < minValue) {
                return false;
            }
        }
        return true;
    }
}

