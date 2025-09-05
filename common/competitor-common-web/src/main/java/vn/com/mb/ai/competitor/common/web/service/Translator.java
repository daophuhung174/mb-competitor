package vn.com.mb.ai.competitor.common.web.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @author hungdp
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Translator {
    private final ResourceBundleMessageSource messageSource;

    public String toLocale(String messageCode) {
        return toLocale(messageCode, null);
    }

    public String toLocale(String messageCode, Object[] objects) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageCode, objects, locale);
    }
}
