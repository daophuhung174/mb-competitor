package com.mbbank.competitor.validator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClientMessageIdValidator implements ConstraintValidator<ValidClientMessageId, HttpServletRequest> {

    @Override
    public void initialize(ValidClientMessageId constraintAnnotation) {
    }

    @Override
    public boolean isValid(HttpServletRequest request, ConstraintValidatorContext context) {
        String clientMessageId = request.getHeader("clientMessageId");
        return clientMessageId != null && !clientMessageId.trim().isEmpty();
    }
}

