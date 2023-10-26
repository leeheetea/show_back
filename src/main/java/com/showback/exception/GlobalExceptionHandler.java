package com.showback.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShowNotFoundException.class)
    @ResponseBody
    public String handleShowNotFound(ShowNotFoundException exception) {
        return exception.getMessage();
    }

    // 예외 핸들러 추가
}
