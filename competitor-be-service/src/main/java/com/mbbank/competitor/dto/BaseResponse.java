package com.mbbank.competitor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@FieldNameConstants
public class BaseResponse<E> {
    @Builder.Default
    private int status = HttpStatus.OK.value();

    @Builder.Default
    private String error = "OK";
    private String clientMessageId;
    private String path;
    private String soaErrorCode;
    private String soaErrorDesc;
    private E data;

    public static <E> BaseResponse<E> ok(String clientMessageId, String path, E data) {
        return BaseResponse.<E>builder()
                .clientMessageId(clientMessageId)
                .path(path)
                .data(data)
                .build();
    }
}
