package vn.com.mb.ai.competitor.common.web.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequest;
import vn.com.mb.ai.competitor.common.constants.Constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author hungdp
 */
@Slf4j
@Configuration
public class DefaultErrorConfiguration {
    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {

            @Override
            public Map<String ,Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions includeStackTrace) {
                Map<String, Object> defaultMap = super.getErrorAttributes(webRequest, includeStackTrace);

                Map<String, Object> errorAttributes = new LinkedHashMap<>();
                errorAttributes.put(Constants.CLIENT_MESSAGE_ID, webRequest.getHeader(Constants.CLIENT_MESSAGE_ID));
                errorAttributes.put("error", defaultMap.get("error"));
                errorAttributes.put("path", defaultMap.get("path"));
                errorAttributes.put("soaErrorCode", String.valueOf(defaultMap.get("status")));
                errorAttributes.put("soaErrorDesc", defaultMap.get("error"));
                errorAttributes.put("status", defaultMap.get("status"));
                errorAttributes.put("data", null);
                log.error(errorAttributes.toString(), errorAttributes);
                return errorAttributes;
            }
        };
    }
}
