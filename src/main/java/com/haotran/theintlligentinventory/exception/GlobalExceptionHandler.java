package com.haotran.theintlligentinventory.exception;

import com.haotran.theintlligentinventory.utils.response.ApiResponse;
import com.haotran.theintlligentinventory.utils.ErrorCode;
import com.haotran.theintlligentinventory.utils.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppxception(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(ApiResponse
                .builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(ApiResponse
                .builder()
                .code(ResponseCode.BAD_REQUEST.getCode())
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidationException(MethodArgumentNotValidException ex) {
        HashMap<String, String> map = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                map.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(ApiResponse
                .builder()
                .code(ResponseCode.BAD_REQUEST.getCode())
                .message(ResponseCode.BAD_REQUEST.getMessage())
                .data(map)
                .build());
    }

    @ExceptionHandler(value = HandlerMethodValidationException.class)
    ResponseEntity<ApiResponse> handleValidationException(HandlerMethodValidationException ex) {
        HashMap<String, String> map = new HashMap<>();
        ex.getParameterValidationResults().forEach(error ->
                map.put(error.getMethodParameter().getParameterName(), error.getResolvableErrors().get(0).getDefaultMessage())
        );

        return ResponseEntity.status(
                HttpStatus.BAD_REQUEST).body(ApiResponse
                .builder()
                .code(ResponseCode.BAD_REQUEST.getCode())
                .message(ResponseCode.BAD_REQUEST.getMessage())
                .data(map)
                .build());
    }
}
