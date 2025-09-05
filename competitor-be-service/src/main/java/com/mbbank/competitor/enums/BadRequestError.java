package com.mbbank.competitor.enums;

import org.springframework.http.HttpStatus;
import vn.com.mb.ai.competitor.common.enums.errors.IError;

public enum BadRequestError implements IError {
    DATA_INVALID(ResponseCode.DATA_INVALID.getCode(), MessagePool.ERROR_DATA_INVALID);

    private final String errorCode;
    private final String message;

    BadRequestError(String errorCode, String message) {
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
        return HttpStatus.BAD_REQUEST.value();
    }
}

