package com.sample.sample.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

public abstract class CustomException extends RuntimeException {

    @Getter
    protected String code = "unknown_error";

    public CustomException(String code) {
        if (!ObjectUtils.isEmpty(code)) {
            this.code = code;
        }
    }

    public CustomException(String code, String message) {
        super(message);
        if (!ObjectUtils.isEmpty(code)) {
            this.code = code;
        }
    }

    public CustomException(String code, String message, Throwable cause) {
        super(message, cause);
        if (!ObjectUtils.isEmpty(code)) {
            this.code = code;
        }
    }

    public CustomException(String code, Throwable cause) {
        super(cause);
        if (!ObjectUtils.isEmpty(code)) {
            this.code = code;
        }
    }

    public abstract HttpStatus getHttpStatus();
}
