package com.rosie.exception;

import com.rosie.response.BaseResponse;
import com.rosie.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ServiceExceptionAdvice {

    @ExceptionHandler({Exception.class})
    public BaseResponse<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return JsonResult.errorResult(e.getMessage());
    }

    @ExceptionHandler({MissingRequestHeaderException.class, AuthException.class})
    public BaseResponse<String> authTokenException(Exception e) {
        log.error(e.getMessage(), e);
        return JsonResult.authFailureResult();
    }
}
