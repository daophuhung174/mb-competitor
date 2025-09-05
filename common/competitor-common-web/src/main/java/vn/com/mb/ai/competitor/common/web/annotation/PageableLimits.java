package vn.com.mb.ai.competitor.common.web.annotation;

import java.lang.annotation.*;

/**
 * @author hungdp
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageableLimits {
    int maxSize() default Integer.MAX_VALUE;
    int minSize() default 1;
}
