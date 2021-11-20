package com.sample.sample.config;

import com.sample.sample.exception.CustomException;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@ControllerAdvice
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
        ErrorResponse res = ErrorResponse.of(ex);
        return handleExceptionInternal(ex, res, new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleCustomException(RuntimeException ex, WebRequest request) {
        ErrorResponse res = ErrorResponse.of(ex);
        log.error("runtime error " + ex + " ", ex);
        return handleExceptionInternal(ex, res, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleCustomException(Exception ex, WebRequest request) {
        ErrorResponse res = ErrorResponse.of(ex);
        log.error("unknown error " + ex + " ", ex);
        return handleExceptionInternal(ex, res, new HttpHeaders(), SERVICE_UNAVAILABLE, request);
    }

    @ToString
    @Getter
    private static class ErrorResponse {

        private String code;
        private String message;

        public static ErrorResponse of(CustomException ex) {
            ErrorResponse res = new ErrorResponse();
            res.code = ex.getCode();
            res.message = ex.getMessage();
            return res;
        }

        public static ErrorResponse of(RuntimeException ex) {
            ErrorResponse res = new ErrorResponse();
            res.code = "internal_server_error";
            res.message = "please contact to service provider.";
            return res;
        }

        public static ErrorResponse of(Exception ex) {
            ErrorResponse res = new ErrorResponse();
            res.code = "unavailable_service";
            res.message = "please contact to service provider.";
            return res;
        }
    }
}
