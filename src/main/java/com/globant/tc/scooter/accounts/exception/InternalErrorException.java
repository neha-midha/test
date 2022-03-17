package com.globant.tc.scooter.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends BaseException{

    public InternalErrorException(AccountsErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getDesc());
    }

    public InternalErrorException(AccountsErrorCode errorCode, String message) {
        super(errorCode.getCode(), message);
    }
}
