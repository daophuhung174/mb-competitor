package vn.com.mb.ai.competitor.common.web.configurations;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author hungdp
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
    private final LoggingInterceptor interceptor;
    private final LimitHeaderInterceptor limitHeaderInterceptor;

    @Value("${app.cors.allowed-origins:*}")
    private String[] allowedOrigins;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(
                new MappedInterceptor(
                    applicationContext.getBeansWithAnnotation(RequestMapping.class).values().stream()
                            .map(Object::getClass)
                            .filter(item -> StringUtils.equals(item.getPackageName(), "vn.com.mbbank.aicenter.beegen.*.controllers"))
                            .map(item -> AnnotationUtils.findAnnotation(item, RequestMapping.class))
                            .filter(Objects::nonNull)
                            .map(RequestMapping::path)
                            .flatMap(Arrays::stream)
                            .map(item -> String.format("%s/**", item))
                            .distinct()
                            .toArray(String[]::new),
                        interceptor)
        );
        registry.addInterceptor(limitHeaderInterceptor);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns(allowedOrigins)
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }

//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver() {
//            @Override
//            public Pageable resolveArgument(MethodParameter methodParameter,
//                                            @Nullable ModelAndViewContainer mavContainer,
//                                            NativeWebRequest webRequest,
//                                            @Nullable WebDataBinderFactory binderFactory) {
//                Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
//                return getLimitsFromAnnotation(pageable, methodParameter);
//            }
//
//            private Pageable getLimitsFromAnnotation(Pageable pageable, MethodParameter methodParameter) {
//                PageableLimits limits = methodParameter.getParameterAnnotation(PageableLimits.class);
//                if (limits == null) {
//                    return pageable;
//                }
//                if (pageable.getPageSize() > limits.maxSize()) {
//                    return PageRequest.of(pageable.getPageNumber(), limits.maxSize(), pageable.getSort());
//                } else if (pageable.getPageSize() < limits.minSize()) {
//                    return PageRequest.of(pageable.getPageNumber(), limits.minSize(), pageable.getSort());
//                }
//                return pageable;
//            }
//        };
//
//        resolver.setMaxPageSize(Integer.MAX_VALUE);
//        resolvers.add(resolver);
//        super.addArgumentResolvers(resolvers);
//    }
}
