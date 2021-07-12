package com.example.togepi.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {

    GENERAL_ERROR(500, "0000"),
    INVALID_PARAM(400, "0001"),
    URL_NOT_FOUND(404, "0002"),
    METHOD_NOT_ALLOWED(405, "0003"),
    PARSE_JSON_ERROR(500, "0005"),
    ES_GENERAL_ERROR(500, "0006"),
    TRANSACTION_NOT_FOUND(404, "0007");

    private final Integer statusCode;
    private final String errorCode;
}
