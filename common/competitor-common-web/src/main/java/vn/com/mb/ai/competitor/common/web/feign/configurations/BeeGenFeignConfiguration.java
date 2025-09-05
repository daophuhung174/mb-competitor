package vn.com.mb.ai.competitor.common.web.feign.configurations;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.logging.log4j.ThreadContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import vn.com.mb.ai.competitor.common.constants.Constants;
import vn.com.mb.ai.competitor.common.web.service.KeycloakClientService;
import vn.com.mb.ai.competitor.common.web.utils.CommonUtils;

/**
 * @author hungdp
 */
@Slf4j
public class BeeGenFeignConfiguration {
    private static final String BEARER_TOKEN_TYPE = "Bearer";

    private final KeycloakClientService keycloakClientService;

    public BeeGenFeignConfiguration(KeycloakClientService keycloakClientService) {
        this.keycloakClientService = keycloakClientService;
    }

    @Bean
    RequestInterceptor interceptor() {
        return template -> {
            String clientMessageId = ThreadContext.get(Constants.CLIENT_MESSAGE_ID);
            template.header(Constants.CLIENT_MESSAGE_ID, clientMessageId == null ? CommonUtils.getClientMessageId() : clientMessageId);
            template.header(Constants.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage());

            // Neu da co thong tin Authorization thi khong lay them
            var auth = template.headers().get(HttpHeaders.AUTHORIZATION);
            if (!CollectionUtils.isEmpty(auth)) {
                return;
            }

            var authentication = SecurityContextHolder.getContext().getAuthentication();

            String token = "";
            if (authentication != null) {
                if (KeycloakAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
                    log.info("Feign Token 1: Using user token");
                    var keycloakSecurityContext = ((KeycloakAuthenticationToken) authentication).getAccount().getKeycloakSecurityContext();
                    token = keycloakSecurityContext.getTokenString();
                } else {
                    log.info("Feign Token 2: Using Admin token");
                    token = keycloakClientService.getServiceAccountAccessToken(false);
                }
            } else {
                log.info("Feign Token 3: Using Admin token");
                token = keycloakClientService.getServiceAccountAccessToken(false);
            }
            template.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", BEARER_TOKEN_TYPE, token));
        };
    }
}
