package vn.com.mb.ai.competitor.common.web.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author hungdp
 */
@Configuration
public class MessagesConfiguration extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    @Value("${spring.messages.encoding}")
    private String encoding;

    @Value("${spring.messages.basename}")
    private String baseName;

    @Value("${spring.messages.cache-duration}")
    private int cacheDuration;

    @Value("${spring.messages.use-code-as-default-message}")
    private boolean useCodeAsDefaultMessage;

    public static final Locale LOCALE_VN = new Locale("vi");

    List<Locale> LOCALES = Arrays.asList(
            new Locale("vi"),
            new Locale("en")
    );

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String language = request.getHeader("Accept-Language");

        return language == null || language.isEmpty() ? LOCALE_VN : Locale.lookup(Locale.LanguageRange.parse(language), LOCALES);

    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(baseName);
        messageSource.setDefaultEncoding(encoding);
        messageSource.setCacheSeconds(cacheDuration);
        messageSource.setUseCodeAsDefaultMessage(useCodeAsDefaultMessage);
        return messageSource;
    }
}
