package vn.com.mb.ai.competitor.common.dto;

/**
 * @author hungdp
 */
public class CommonMessagePool {
    public static final String ERROR_DATA_INVALID = "error.data_invalid";
    public static final String ERROR_FORBIDDEN = "error.forbidden";
    public static final String ERROR_UNAUTHORIZED = "error.unauthorized";
    public static final String ERROR_INTERNAL_ERROR = "error.internal_error";
    public static final String ERROR_DATA_NOT_FOUND = "error.data_not_found";
    public static final String ERROR_UPLOAD_FILE_EXCEEDED = "error.upload_file_exceeded";
    public static final String ERROR_HEADERS_INVALID = "error.headers_invalid";

    private CommonMessagePool() {
        throw new IllegalStateException("Utility class");
    }
}
