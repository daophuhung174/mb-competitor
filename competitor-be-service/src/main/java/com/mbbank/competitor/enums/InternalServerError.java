package com.mbbank.competitor.enums;

import org.springframework.http.HttpStatus;
import vn.com.mb.ai.competitor.common.enums.errors.IError;

/**
 * @author datdt2.os
 */
public enum InternalServerError implements IError {
    UNKNOWN(ResponseCode.UNKNOWN.getCode(), MessagePool.ERROR_UNKNOWN);

    private final String errorCode;
    private final String message;

    InternalServerError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
