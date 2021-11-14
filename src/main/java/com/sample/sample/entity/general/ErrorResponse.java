package com.sample.sample.entity.general;

import com.sample.sample.exception.CustomException;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorResponse {

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
