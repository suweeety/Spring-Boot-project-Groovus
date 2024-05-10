package com.groovus.www.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {

    @GetMapping("/exception")
    public ResponseEntity<?> exceptionTest() {
        throw new RequestException(ErrorCode.PROJECT_NOT_FOUND_404);
    }

}
