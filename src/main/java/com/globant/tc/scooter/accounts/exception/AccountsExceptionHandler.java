package com.globant.tc.scooter.accounts.exception;

import com.globant.tc.scooter.accounts.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class AccountsExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Error> handleException(Exception e) {
        e.printStackTrace();
        Error error = new Error().code(AccountsErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message(AccountsErrorCode.INTERNAL_SERVER_ERROR.getDesc());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Error> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        Error error = new Error().code(AccountsErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message(AccountsErrorCode.INTERNAL_SERVER_ERROR.getDesc());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach(er -> {
            message.append(er.getDefaultMessage()).append(" ");
        });
        Error error = new Error().code(AccountsErrorCode.INVALID_INPUT.getCode()).message(message.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Error> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        Error error = new Error().code(AccountsErrorCode.INVALID_INPUT.getCode())
                .message(AccountsErrorCode.INVALID_INPUT.getDesc());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Error> handleNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(formErrorResponse(e), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidInputException.class)
    public ResponseEntity<Error> handleInvalidInputException(InvalidInputException e) {
        return new ResponseEntity<>(formErrorResponse(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InternalErrorException.class)
    public ResponseEntity<Error> handleInternalErrorException(InternalErrorException e) {
        return new ResponseEntity<>(formErrorResponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Error formErrorResponse(BaseException e) {
        Error error = new Error();
        error.setCode(e.getCode());
        error.setMessage(e.getDesc());
        return error;
    }
}
