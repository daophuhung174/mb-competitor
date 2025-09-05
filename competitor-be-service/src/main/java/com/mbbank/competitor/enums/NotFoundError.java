package com.mbbank.competitor.enums;

import org.springframework.http.HttpStatus;
import vn.com.mb.ai.competitor.common.enums.errors.IError;

public enum NotFoundError implements IError {
    DATA_NOT_FOUND(ResponseCode.DATA_NOT_FOUND.getCode(), MessagePool.ERROR_DATA_NOT_FOUND);

    private final String errorCode;
    private final String message;

    NotFoundError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}
