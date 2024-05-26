package com.groovus.www.exception;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ResponseExeptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<Object> handleCustomException(RequestException e) {
        ErrorResponseDTO responseDTO = ErrorResponseDTO.createDTO(e.getErrorCode(), e.getMessage(), e.getHttpStatus());
        return new ResponseEntity<>(responseDTO, e.getHttpStatus());
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorResponseDTO {
        private String errorCode;
        private String message;
        private HttpStatus httpStatus;

        public static ErrorResponseDTO createDTO(String errorCode, String message, HttpStatus httpStatus) {
            return ErrorResponseDTO.builder()
                    .errorCode(errorCode)
                    .message(message)
                    .httpStatus(httpStatus)
                    .build();
        }

    }


}

