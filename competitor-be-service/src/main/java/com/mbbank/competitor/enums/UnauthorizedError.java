package com.mbbank.competitor.enums;

import org.springframework.http.HttpStatus;
import vn.com.mb.ai.competitor.common.enums.errors.IError;

/**
 * @author datdt2.os
 */
public enum UnauthorizedError implements IError {
    UNAUTHORIZED(ResponseCode.UNAUTHORIZED.getCode(), MessagePool.ERROR_UNAUTHORIZED);

    private final String errorCode;
    private final String message;

    UnauthorizedError(String errorCode, String message) {
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
        return HttpStatus.UNAUTHORIZED.value();
    }
}
