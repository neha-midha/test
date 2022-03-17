package com.globant.tc.scooter.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends BaseException{

    public InvalidInputException(AccountsErrorCode errorCode) {
        super(errorCode.getCode(), errorCode.getDesc());
    }

    public InvalidInputException(AccountsErrorCode errorCode, String message) {
        super(errorCode.getCode(), message);
    }

}
