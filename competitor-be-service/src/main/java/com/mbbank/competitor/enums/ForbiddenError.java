package com.mbbank.competitor.enums;

import org.springframework.http.HttpStatus;
import vn.com.mb.ai.competitor.common.enums.errors.IError;

/**
 * @author datdt2.os
 */
public enum ForbiddenError implements IError {
    FORBIDDEN_ERROR(ResponseCode.FORBIDDEN.getCode(), MessagePool.ERROR_FORBIDDEN);

    private final String errorCode;
    private final String message;

    ForbiddenError(String errorCode, String message) {
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
        return HttpStatus.FORBIDDEN.value();
    }
}
