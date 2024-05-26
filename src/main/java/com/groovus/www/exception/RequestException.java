package com.groovus.www.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RequestException extends RuntimeException{

    private String errorCode;
    private final HttpStatus httpStatus;
    private String message;

    public RequestException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.toString();
    }

    public RequestException(ErrorCode errorCode, String message) {
        super(message);
        this.httpStatus = errorCode.getHttpStatus();
        this.message = message;
        this.errorCode = errorCode.toString();
    }


}
