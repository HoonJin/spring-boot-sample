package com.sample.sample.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {

    public BadRequestException(String code) {
        super(code);
    }

    public BadRequestException(String code, String message) {
        super(code, message);
    }

    public BadRequestException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public BadRequestException(String code, Throwable cause) {
        super(code, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

}
