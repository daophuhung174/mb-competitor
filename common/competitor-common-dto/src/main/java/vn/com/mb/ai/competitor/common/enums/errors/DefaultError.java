package vn.com.mb.ai.competitor.common.enums.errors;

import vn.com.mb.ai.competitor.common.dto.CommonMessagePool;

/**
 * @author hungdp
 */
public class DefaultError implements IError {
    private final String errorCode;
    private final String message;
    private int httpStatus = 500;
    
    public DefaultError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
    
    public DefaultError(String errorCode, String message, int httpStatus) {
        this(errorCode, message);
        this.httpStatus = httpStatus;
    }
    
    public DefaultError(String errorCode) {
        this(errorCode, CommonMessagePool.ERROR_INTERNAL_ERROR);
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
        return httpStatus;
    }
}
