package com.globant.tc.scooter.accounts.exception;

public enum AccountsErrorCode {

    ACCOUNT_NOT_FOUND(4000, "Account not found"),
    USER_NOT_FOUND(4001, "User not found"),
    INVALID_INPUT(4002, "Invalid input"),
    INTERNAL_SERVER_ERROR(5000, "Internal Server Error. Please contact admin");

    private Integer code;
    private String desc;

    AccountsErrorCode(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
