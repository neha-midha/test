package com.globant.tc.scooter.accounts.exception;

public class BaseException extends RuntimeException{

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private String desc;

    BaseException(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
