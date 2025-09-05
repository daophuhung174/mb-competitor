package vn.com.mb.ai.competitor.common.enums.errors;

import vn.com.mb.ai.competitor.common.dto.CommonMessagePool;
import vn.com.mb.ai.competitor.common.dto.CommonResponseCode;
import vn.com.mb.ai.competitor.common.exception.http.ResponseException;

/**
 * @author hungdp
 */
public interface IError {
    IError INTERNAL_ERROR = new DefaultError(CommonResponseCode.UNKNOWN.getCode(), CommonMessagePool.ERROR_INTERNAL_ERROR, 500);
    IError DATA_INVALID_ERROR = new DefaultError(CommonResponseCode.DATA_INVALID.getCode(), CommonMessagePool.ERROR_DATA_INVALID, 400);
    IError HEADERS_INVALID_ERROR = new DefaultError(CommonResponseCode.HEADERS_INVALID.getCode(), CommonMessagePool.ERROR_HEADERS_INVALID, 400);
    IError FORBIDDEN_ERROR = new DefaultError(CommonResponseCode.FORBIDDEN.getCode(), CommonMessagePool.ERROR_FORBIDDEN, 403);


    String getErrorCode();
    String getMessage();
    int getHttpStatus();

    default ResponseException exception(Object... objects) {
        return new ResponseException(this, null, objects);
    }

    default  ResponseException exceptionWithMessage(String message, Object... objects) {
        return new ResponseException(this, message, objects);
    }

    default  ResponseException exceptionCause(Throwable throwable, Object... objects) {
        return new ResponseException(throwable, this, null, objects);
    }
}
