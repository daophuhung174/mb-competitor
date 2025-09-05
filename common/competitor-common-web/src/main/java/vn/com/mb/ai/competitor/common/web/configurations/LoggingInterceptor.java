package vn.com.mb.ai.competitor.common.web.configurations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import vn.com.mb.ai.competitor.common.web.utils.JsonUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author hungdp
 */
@Slf4j
@ControllerAdvice
public class LoggingInterceptor extends RequestBodyAdviceAdapter implements HandlerInterceptor, ResponseBodyAdvice<Object> {
    private static final String START_TIME = "startTime";
    private static final String SERVICE_HEADER = "serviceHeader";
    private static final String SERVICE_MESSAGE_ID = "serviceMessageId";
    private static final String LOG_TYPE = "logType";
    private static final String DURATION = "duration";
    private static final String HTTP_REQUEST = "httpRequest";
    private static final String HTTP_RESPONSE = "httpResponse";
    private static final String RESPONSE_CODE = "responseCode";
    private static final String CLIENT_MESSAGE_ID = "clientMessageId";

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;

    public LoggingInterceptor(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    private static String getRequestFullURL(HttpServletRequest request) {
        var requestURL = new StringBuilder(request.getRequestURL().toString());
        var queryString = request.getQueryString();
        return queryString == null ? requestURL.toString() : requestURL.append("?").append(queryString).toString();
    }

    private static ServiceHeader createServiceHeader(HttpServletRequest httpServletRequest) {
        var servicePath = httpServletRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        var xForwardedFor = httpServletRequest.getHeader("x-forwarded-for");
        String currentUser = null;
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            currentUser = String.valueOf(auth.getPrincipal());
        }

        return ServiceHeader.builder()
                .clientMessageId(httpServletRequest.getHeader(CLIENT_MESSAGE_ID) == null ? UUID.randomUUID().toString() : httpServletRequest.getHeader(CLIENT_MESSAGE_ID))
                .servicePath(servicePath != null ? servicePath.toString() : null)
                .httpPath(getRequestFullURL(httpServletRequest))
                .httpMethod(httpServletRequest.getMethod())
                .messageTimestamp(new Date())
                .sourceAppIp(xForwardedFor == null ? httpServletRequest.getRemoteHost() : xForwardedFor)
                .destAppIp(httpServletRequest.getLocalAddr())
                .destAppPort(httpServletRequest.getLocalPort())
                .authenticationUser(currentUser)
                .authorization(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION))
                .build();
    }

    /**
     * Xu ly cho method GET/DELETE
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        // Method co body la null
        if (StringUtils.equalsAny(request.getMethod(), HttpMethod.GET.name(), HttpMethod.DELETE.name())
                || (StringUtils.equalsAny(request.getMethod(), HttpMethod.POST.name(), HttpMethod.PUT.name())
                && (request.getContentLengthLong() < 5L ||  (request.getContentType() != null
                && request.getContentType().toLowerCase().contains("multipart/form-data"))))
            // De length la < 5L de cac truong hop gui body la {} van xu ly duoc
        ) {
            request.setAttribute(START_TIME, System.nanoTime());
            var serviceHeader = createServiceHeader(request);
            request.setAttribute(SERVICE_HEADER, serviceHeader);
            ThreadContext.put(CLIENT_MESSAGE_ID, serviceHeader.getClientMessageId());
            ThreadContext.put(SERVICE_MESSAGE_ID, serviceHeader.getServiceMessageId());
            ThreadContext.put(SERVICE_HEADER, serviceHeader.toString());
            ThreadContext.put(LOG_TYPE, HTTP_REQUEST);
            ThreadContext.remove(DURATION);
            log.info(
                    HttpMessageObject.builder()
                            .sourceAppIp(httpServletRequest.getRemoteHost())
                            .destAppIp(httpServletRequest.getLocalAddr())
                            .destAppPort(httpServletRequest.getLocalPort())
                            .httpPath(httpServletRequest.getRequestURI())
                            .httpMethod(httpServletRequest.getMethod())
                            .responseCode(httpServletResponse.getStatus())
                            .header(
                                    Collections.list(request.getHeaderNames()).stream()
                                            .collect(
                                                    Collectors.toMap(
                                                            k -> k,
                                                            k -> {
                                                                if (StringUtils.equalsIgnoreCase(k, HttpHeaders.AUTHORIZATION)) {
                                                                    return "<<Not recorded to log>>";
                                                                }
                                                                return request.getHeader(k);
                                                            }))
                            )
                            .build()
                            .toString()
            );
            ThreadContext.remove(LOG_TYPE);
        }
        return true;
    }

    /**
     * Xu ly cho method PUT/POST
     * @param body
     * @param inputMessage
     * @param parameter
     * @param targetType
     * @param converterType
     * @return
     */
    @Override
    public @NonNull Object afterBodyRead(
            @NonNull Object body,
            @NonNull HttpInputMessage inputMessage,
            @NonNull MethodParameter parameter,
            @NonNull Type targetType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        this.httpServletRequest.setAttribute(START_TIME, System.nanoTime());
        var serviceHeader = createServiceHeader(this.httpServletRequest);
        this.httpServletRequest.setAttribute(SERVICE_HEADER, serviceHeader);
        ThreadContext.put(CLIENT_MESSAGE_ID, serviceHeader.getClientMessageId());
        ThreadContext.put(SERVICE_MESSAGE_ID, serviceHeader.getServiceMessageId());
        ThreadContext.put(SERVICE_HEADER, serviceHeader.toString());
        ThreadContext.put(LOG_TYPE, HTTP_REQUEST);
        ThreadContext.remove(DURATION);
        log.info(
                HttpMessageObject.builder()
                        .sourceAppIp(httpServletRequest.getRemoteHost())
                        .destAppIp(httpServletRequest.getLocalAddr())
                        .destAppPort(httpServletRequest.getLocalPort())
                        .httpPath(httpServletRequest.getRequestURI())
                        .httpMethod(httpServletRequest.getMethod())
                        .responseCode(httpServletResponse.getStatus())
                        .header(
                                Collections.list(httpServletRequest.getHeaderNames()).stream()
                                        .collect(
                                                Collectors.toMap(
                                                        k -> k,
                                                        k -> {
                                                            if (StringUtils.equalsIgnoreCase(k, HttpHeaders.AUTHORIZATION)) {
                                                                return "<<Not recorded to log>>";
                                                            }
                                                            return httpServletRequest.getHeader(k);
                                                        }))
                        )
                        .build()
                        .toString()
        );
        ThreadContext.remove(LOG_TYPE);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    /**
     * Xu ly truoc khi tra du lieu ve
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {
        var serviceMessageId = ThreadContext.get(SERVICE_MESSAGE_ID);
        if (serviceMessageId == null) {
            return body;
        }
        response.getHeaders().set(SERVICE_MESSAGE_ID, serviceMessageId);
        long elapsed = -1;
        var startTime = httpServletRequest.getAttribute(START_TIME);
        if (startTime != null) {
            elapsed = System.nanoTime() - (Long) startTime;
        }
        ThreadContext.put(LOG_TYPE, HTTP_RESPONSE);
        ThreadContext.put(DURATION, String.format("%.3f", (double) elapsed / 1000000.0D));
        ThreadContext.put(RESPONSE_CODE, String.valueOf(httpServletResponse.getStatus()));
        log.info(
                HttpMessageObject.builder()
                        .sourceAppIp(httpServletRequest.getRemoteHost())
                        .destAppIp(httpServletRequest.getLocalAddr())
                        .destAppPort(httpServletRequest.getLocalPort())
                        .httpPath(httpServletRequest.getRequestURI())
                        .httpMethod(httpServletRequest.getMethod())
                        .responseCode(httpServletResponse.getStatus())
                        .header(
                                httpServletResponse.getHeaderNames().stream()
                                        .distinct()
                                        .collect(Collectors.toMap(k -> k, k -> httpServletResponse.getHeaders(k)
                                                .parallelStream()
                                                .collect(Collectors.joining(";"))))
                        )
                        .body(JsonUtil.toJson(body))
                        .build()
                        .toString()
        );
        ThreadContext.remove(LOG_TYPE);
        ThreadContext.remove(SERVICE_MESSAGE_ID);
        ThreadContext.remove(RESPONSE_CODE);

        return body;
    }

    @Override
    public boolean supports(
            @NonNull MethodParameter methodParameter,
            @NonNull Type targetType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public boolean supports(
            @NonNull MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceHeader {
        private String clientMessageId;

        @Setter(AccessLevel.NONE)
        private String serviceMessageId;

        private String servicePath;
        private String httpPath;
        private String httpMethod;
        private Date messageTimestamp;
        private String sourceAppId;
        private String sourceAppIp;
        private String destAppIp;
        private int destAppPort;
        private String authenticationUser;

        @JsonIgnore
        private String authorization;

        public String getServiceMessageId() {
            return (sourceAppId != null || clientMessageId != null)
                    ? String.format("%s-%s", sourceAppId, clientMessageId)
                    : null;
        }

        @Override
        public String toString() {
            return JsonUtil.toJson(this);
        }
    }

    @Data
    @Builder
    private static class HttpMessageObject {
        private String sourceAppIp;
        private String destAppIp;
        private int destAppPort;
        private String httpPath;
        private String httpMethod;
        private int responseCode;
        private Map<String, String> header;
        private String body;

        @Override
        public String toString() {
            return JsonUtil.toJson(this);
        }

    }
}
