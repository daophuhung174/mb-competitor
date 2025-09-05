package com.mbbank.competitor.enums;

public enum ResponseCode {
    /*
        1* Loi network, security, system
        2* Loi validate input
        3* Loi xac thuc va phan quyen
        4* Loi not found
        5* Loi logic nghiep vu
        6* Loi khac
    */
    UNKNOWN("1000"),
    FORMAT_FILE_FOR_UPLOAD("1001"),

    DATA_INVALID("2000"),
    REQUIRED("2001"),

    UNAUTHORIZED("3000"),
    FORBIDDEN("3001"),

    DATA_NOT_FOUND("4000"),
    USER_NOT_FOUND("4001"),

    LOGIC_INVALID("5000"),

    OTHER("6000");


    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

