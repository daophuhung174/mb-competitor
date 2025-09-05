//package com.mbbank.competitor.validator;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//import java.util.List;
//
//public class NotBlankElementsValidator implements ConstraintValidator<NotBlankElements, List<String>> {
//    @Override
//    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
//        if (list == null) return true;
//
//        return list.stream().allMatch(item -> item != null && !item.trim().isEmpty());
//    }
//}
