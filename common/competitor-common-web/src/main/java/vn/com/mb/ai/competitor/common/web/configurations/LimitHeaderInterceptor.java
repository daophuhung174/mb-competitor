package vn.com.mb.ai.competitor.common.web.configurations;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import vn.com.mb.ai.competitor.common.constants.Constants;
import vn.com.mb.ai.competitor.common.enums.errors.IError;
import vn.com.mb.ai.competitor.common.exception.http.ResponseException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hungdp
 */
@Data
@Component
public class LimitHeaderInterceptor implements HandlerInterceptor {
    private List<String> headers = List.of(
            HttpHeaders.ACCEPT,
            HttpHeaders.ACCEPT_CHARSET,
            HttpHeaders.ACCEPT_ENCODING,
            HttpHeaders.ACCEPT_LANGUAGE,
            HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS,
            HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.CACHE_CONTROL,
            HttpHeaders.CONNECTION,
            HttpHeaders.CONTENT_LANGUAGE,
            HttpHeaders.CONTENT_ENCODING,
            HttpHeaders.CONTENT_DISPOSITION,
            HttpHeaders.CONTENT_LENGTH,
            HttpHeaders.CONTENT_LOCATION,
            HttpHeaders.CONTENT_TYPE,
            HttpHeaders.COOKIE,
            HttpHeaders.DATE,
            HttpHeaders.EXPECT,
            HttpHeaders.FROM,
            HttpHeaders.HOST,
            HttpHeaders.LINK,
            HttpHeaders.MAX_FORWARDS,
            HttpHeaders.ORIGIN,
            HttpHeaders.PRAGMA,
            HttpHeaders.PROXY_AUTHORIZATION,
            HttpHeaders.RANGE,
            HttpHeaders.REFERER,
            HttpHeaders.TRAILER,
            HttpHeaders.TRANSFER_ENCODING,
            HttpHeaders.USER_AGENT,
            HttpHeaders.UPGRADE,
            Constants.CLIENT_MESSAGE_ID,
            Constants.VERIFY,
            Constants.POSTMAN_TOKEN,
            Constants.X_KL_AJAX_REQUEST,
            Constants.ALT_SVC,
            Constants.ALT_USED,
            Constants.ATTRIBUTION_REPORTING_ELIGIBLE,
            Constants.CONTENT_DIGEST,
            Constants.DEVICE_MEMORY,
            Constants.DNT,
            Constants.DOWNLINK,
            Constants.DPR,
            Constants.EARLY_DATA,
            Constants.ECT,
            Constants.FORWARDED,
            Constants.IF_MATCH,
            Constants.IF_MODIFIED_SINCE,
            Constants.IF_NONE_MATCH,
            Constants.IF_RANGE,
            Constants.IF_UNMODIFIED_SINCE,
            Constants.KEEP_ALIVE,
            Constants.LAST_MODIFIED,
            Constants.PRIORITY,
            Constants.REPR_DIGEST,
            Constants.RTT,
            Constants.X_REQUEST_ID,
            Constants.X_REAL_IP,
            Constants.X_FORWARDED_HOST,
            Constants.X_FORWARDED_FOR,
            Constants.X_FORWARDED_PORT,
            Constants.X_FORWARDED_PROTO,
            Constants.X_FORWARDED_SCHEME,
            Constants.X_SCHEME,
            Constants.SAVE_DATA,
            Constants.SEC_BROWSING_TOPICS,
            Constants.SEC_CH_PREFERS_COLOR_SCHEME,
            Constants.SEC_CH_PREFERS_REDUCED_MOTION,
            Constants.SEC_CH_PREFERS_REDUCED_TRANSPARENCY,
            Constants.SEC_CH_UA,
            Constants.SEC_CH_UA_ARCH,
            Constants.SEC_CH_UA_BITNESS,
            Constants.SEC_CH_UA_FULL_VERSION,
            Constants.SEC_CH_UA_FULL_VERSION_LIST,
            Constants.SEC_CH_UA_MOBILE,
            Constants.SEC_CH_UA_MODEL,
            Constants.SEC_CH_UA_PLATFORM,
            Constants.SEC_CH_UA_PLATFORM_VERSION,
            Constants.SEC_FETCH_DEST,
            Constants.SEC_FETCH_MODE,
            Constants.SEC_FETCH_SITE,
            Constants.SEC_FETCH_USER,
            Constants.SEC_GPC,
            Constants.SEC_PURPOSE,
            Constants.SEC_WEBSOCKET_EXTENSIONS,
            Constants.SEC_WEBSOCKET_KEY,
            Constants.SEC_WEBSOCKET_PROTOCOL,
            Constants.SERVICE_WORKER_NAVIGATION_PRELOAD,
            Constants.TE,
            Constants.UPGRADE_INSECURE_REQUESTS,
            Constants.VIA,
            Constants.VIEWPORT_WIDTH,
            Constants.WANT_CONTENT_DIGEST,
            Constants.WANT_DIGEST,
            Constants.WANT_REPR_DIGEST,
            Constants.WARNING,
            Constants.WIDTH,
            Constants.X_ORIGINAL_FORWARDED_FOR,
            Constants.PURPOSE
    );

    private Set<String> lowerCaseHeaders;

    @PostConstruct
    private void postConstruct(){
        lowerCaseHeaders = headers.stream().map(String::toLowerCase).collect(Collectors.toSet());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> enumerationHeaders =  request.getHeaderNames();
        if(!Objects.isNull(enumerationHeaders)){
            String headerFromRequest = "";
            while(enumerationHeaders.hasMoreElements()){
                headerFromRequest = enumerationHeaders.nextElement();
                if(lowerCaseHeaders.contains(headerFromRequest)){
                    continue;
                }
                throw new ResponseException(IError.HEADERS_INVALID_ERROR, Collections.list(request.getHeaderNames()).toString());
            }
        }
        return true;
    }
}

