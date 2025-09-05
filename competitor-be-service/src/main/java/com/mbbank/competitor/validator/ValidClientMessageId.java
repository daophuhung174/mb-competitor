package com.mbbank.competitor.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClientMessageIdValidator.class)
public @interface ValidClientMessageId {
    String message() default "clientMessageId header is missing";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
