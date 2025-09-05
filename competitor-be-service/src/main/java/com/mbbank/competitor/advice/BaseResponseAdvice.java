package com.mbbank.competitor.advice;

import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@ControllerAdvice
@AllArgsConstructor
public class BaseResponseAdvice implements ResponseBodyAdvice<Object> {

    private static final String CLIENT_MESSAGE_ID = "clientMessageId";

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(WrappedWithBaseResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        String clientMessageId = request.getHeaders().getFirst(CLIENT_MESSAGE_ID);
        String requestUri = request.getURI().toString();

        vn.com.mb.ai.competitor.dto.BaseResponse<Object> baseResponse = vn.com.mb.ai.competitor.dto.BaseResponse.ok(clientMessageId, requestUri, body);

        return ResponseEntity.ok(baseResponse).getBody();
    }
}

