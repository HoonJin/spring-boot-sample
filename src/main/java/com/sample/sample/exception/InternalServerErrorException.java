package com.sample.sample.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends CustomException {

    public InternalServerErrorException(String code) {
        super(code);
    }

    public InternalServerErrorException(String code, String message) {
        super(code, message);
    }

    public InternalServerErrorException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public InternalServerErrorException(String code, Throwable cause) {
        super(code, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
