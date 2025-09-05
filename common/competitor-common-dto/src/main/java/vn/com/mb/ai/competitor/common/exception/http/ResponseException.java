package vn.com.mb.ai.competitor.common.exception.http;

import lombok.Getter;
import vn.com.mb.ai.competitor.common.enums.errors.DefaultError;
import vn.com.mb.ai.competitor.common.enums.errors.IError;

/**
 * @author hungdp
 */
@Getter
public class ResponseException extends RuntimeException {
    private final IError error;
    private final Object[] params;

    public ResponseException(IError error, String message, Object... params) {
        this(null, error, message, params);
    }

    public ResponseException(Throwable throwable, IError error, String message, Object... params) {
        super(message == null ? error.getMessage() : message, throwable);
        this.error = error;
        this.params = params == null ? new Object[0] : params;
    }

    public ResponseException(IError error, Object... params) {
        this(error, null, params);
    }

    public ResponseException(String errorCode, String message) {
        this(new DefaultError(errorCode, message));
    }

    public String getErrorCode() {
        return error.getErrorCode();
    }
}
