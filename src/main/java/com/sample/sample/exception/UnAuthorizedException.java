package com.sample.sample.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException(String code) {
        super(code);
    }

    public UnAuthorizedException(String code, String message) {
        super(code, message);
    }

    public UnAuthorizedException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public UnAuthorizedException(String code, Throwable cause) {
        super(code, cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

}
