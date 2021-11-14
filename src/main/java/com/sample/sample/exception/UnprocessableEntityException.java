package com.sample.sample.exception;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends CustomException {

    public UnprocessableEntityException(String code) {
        super(code);
    }

    public UnprocessableEntityException(String code, String message) {
        super(code, message);
    }

    public UnprocessableEntityException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public UnprocessableEntityException(String code, Throwable cause) {
        super(code, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }

}
