package com.example.togepi.advice;

import com.example.togepi.dto.ErrorDto;
import com.example.togepi.exception.BaseException;
import com.example.togepi.exception.ClientException;
import com.example.togepi.exception.ServerException;
import com.example.togepi.type.ErrorType;
import com.example.togepi.util.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ControllerAdviceMapper {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorDto> handleClientException(ClientException e) {
        return buildFlatResponseError(e);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorDto> handleServerException(ServerException e) {
        logError(e);
        return buildFlatResponseError(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleDefaultException(Exception e) {
        final ServerException ex = new ServerException(ErrorType.GENERAL_ERROR, e);
        logError(ex);
        return buildFlatResponseError(ex);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorDto> handleInvalidParam(Exception e) {
        final ClientException ex = new ClientException(ErrorType.INVALID_PARAM, e, e.getMessage());
        return buildFlatResponseError(ex);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<ErrorDto> handleNoHandlerFoundException(NoHandlerFoundException e) {
        final ClientException ex = new ClientException(ErrorType.URL_NOT_FOUND, e, e.getMessage());
        return buildFlatResponseError(ex);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDto> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        final ClientException ex = new ClientException(ErrorType.METHOD_NOT_ALLOWED, e, e.getMessage());
        return buildFlatResponseError(ex);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDto> handleMissingQueryParamException(MissingServletRequestParameterException e) {
        final ClientException ex = new ClientException(ErrorType.INVALID_PARAM, e, e.getMessage());
        return buildFlatResponseError(ex);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorType errorType = ErrorType.INVALID_PARAM;

        final List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();

        final List<ErrorDto.Error> errors = fieldErrors.stream().map(fieldError -> {
            final ErrorDto.Error errorResponse = new ErrorDto.Error();
            errorResponse.setCode(errorType.getErrorCode());
            errorResponse.setUserMessage(fieldError.getDefaultMessage());
            errorResponse.setInternalMessage("");

            return errorResponse;

        }).collect(Collectors.toList());

        final ErrorDto errorResponseDto = new ErrorDto();
        errorResponseDto.setErrors(errors);

        return new ResponseEntity<>(errorResponseDto, HttpStatus.valueOf(errorType.getStatusCode()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException e) {
        final String errorMessage = getConstraintValidationErrorMessage(e.getMessage());
        final ClientException ex = new ClientException(ErrorType.INVALID_PARAM, e, errorMessage);
        return buildFlatResponseError(ex);
    }

    private String getConstraintValidationErrorMessage(String message) {
        final String[] errorMessages = message.split(",");
        return errorMessages.length > 1 ? errorMessages[0] : message;
    }

    private String translateMessage(BaseException e) {
        return I18nUtil.translate(e.getUserMessage(), e.getUserMessageArgs());
    }

    private void logError(BaseException e) {
        MDC.put("Response-User-Error-Message", translateMessage(e));
        MDC.put("Response-Error-Code", e.getErrorType().getErrorCode());
        log.error(e.getMessage(), e);
    }

    private ResponseEntity<ErrorDto> buildFlatResponseError(BaseException e) {
        final ErrorDto.Error errorResponse = new ErrorDto.Error();
        errorResponse.setCode(e.getErrorType().getErrorCode());
        errorResponse.setInternalMessage(e.getMessage());
        errorResponse.setUserMessage(I18nUtil.translate(e.getUserMessage(), e.getUserMessageArgs()));

        final ErrorDto errorResponseDto = new ErrorDto();
        errorResponseDto.setErrors(Collections.singletonList(errorResponse));

        return new ResponseEntity<>(errorResponseDto, HttpStatus.valueOf(e.getErrorType().getStatusCode()));
    }
}
