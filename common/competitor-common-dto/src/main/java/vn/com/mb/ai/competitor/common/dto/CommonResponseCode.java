package vn.com.mb.ai.competitor.common.dto;

/**
 * @author hungdp
 */
public enum CommonResponseCode {
    SUCCESS("0000"),
    UNKNOWN("1000"),
    FORBIDDEN("3001"),
    UNAUTHORIZED("3000"),
    DATA_NOT_FOUND("4000"),
    DATA_INVALID("2000"),
    HEADERS_INVALID("2001"),
    OTHER("6000");

    private final String code;

    CommonResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
