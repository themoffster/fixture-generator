package com.uk.harhay.ersda.controllers.advice;

import static com.uk.harhay.ersda.controllers.advice.ExceptionControllerAdvice.ORDER;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Order(ORDER)
@ControllerAdvice
public class ExceptionControllerAdvice {

    public static final int ORDER = 10;

    @ResponseBody
    @ExceptionHandler
    ResponseEntity<Map<String, Object>> handle(ConstraintViolationException e) {
        log.info("Handling ConstraintViolationException.", e);
        return buildResponse(BAD_REQUEST, e);
    }

    @SuppressWarnings("SameParameterValue")
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatusCode statusCode, Exception exception) {
        Map<String, Object> body = new HashMap<>();
        body.put("code", statusCode.value());
        body.put("message", exception.getMessage());
        return ResponseEntity.status(statusCode).body(body);
    }
}
