package com.example.togepi.exception;

import com.example.togepi.type.ErrorType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public abstract class BaseException extends RuntimeException {

    protected ErrorType errorType;
    protected String userMessage;
    protected Object[] userMessageArgs;

    protected BaseException(Throwable e) {
        super(e);
    }

    protected BaseException(String message) {
        super(message);
    }
}
