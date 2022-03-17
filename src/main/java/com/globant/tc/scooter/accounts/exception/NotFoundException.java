package com.globant.tc.scooter.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BaseException{

    public NotFoundException(AccountsErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getDesc());
    }

    public NotFoundException(AccountsErrorCode errorCode, String message) {
        super(errorCode.getCode(), message);
    }
}
