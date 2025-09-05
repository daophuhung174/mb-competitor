package vn.com.mb.ai.competitor.common.web.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author hungdp
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
    private List<Object> acceptedValues;

    @Override
    public void initialize(EnumValue annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants()).map(Enum::toString)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return acceptedValues.contains(value.toString());
    }
}
