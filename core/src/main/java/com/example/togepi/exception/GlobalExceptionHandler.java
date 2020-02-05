package com.example.togepi.exception;

import com.example.togepi.dto.ErrorResponseDto;
import com.example.togepi.enums.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.SocketTimeoutException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorResponseDto> handleBadInput(Exception e) {
        log.error(e.getMessage(), e);
        final Error error = Error.INVALID_PARAM;
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(error.getErrorCode(), error.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.valueOf(error.getStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        final Error error = Error.INVALID_PARAM;
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(error.getErrorCode(), e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.valueOf(error.getStatusCode()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        final Error error = Error.URL_NOT_FOUND;
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(error.getErrorCode(), error.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.valueOf(error.getStatusCode()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        final Error error = Error.METHOD_NOT_ALLOWED;
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(error.getErrorCode(), error.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.valueOf(error.getStatusCode()));
    }

    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<ErrorResponseDto> handleTimeoutException(SocketTimeoutException e) {
        log.error(e.getMessage(), e);
        final Error error = Error.TIMEOUT_ERROR;
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(error.getErrorCode(), error.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.valueOf(error.getStatusCode()));
    }
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(Exception e) {
        log.error(e.getMessage(), e);
        final Error error = Error.RESOURCE_NOT_FOUND;
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(error.getErrorCode(), error.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.valueOf(error.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception e) {
        log.error(e.getMessage(), e);
        final Error error = Error.GENERAL_ERROR;
        final ErrorResponseDto errorResponseDto = new ErrorResponseDto(error.getErrorCode(), error.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.valueOf(error.getStatusCode()));
    }
}