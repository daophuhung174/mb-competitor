package com.mbbank.competitor.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {
    private String[] protocols;

    @Override
    public void initialize(ValidUrl constraintAnnotation) {
        this.protocols = constraintAnnotation.protocols();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Consider null or empty as valid, use @NotNull/@NotEmpty for null/empty check
        }

        try {
            URL url = new URL(value);
            return Arrays.asList(protocols).contains(url.getProtocol());
        } catch (MalformedURLException e) {
            return false;
        }
    }
}

