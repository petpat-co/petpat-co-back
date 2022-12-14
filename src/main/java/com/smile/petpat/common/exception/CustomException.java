package com.smile.petpat.common.exception;

import com.smile.petpat.common.response.ErrorCode;

public class CustomException extends RuntimeException {

    public ErrorCode errorCode;

    public CustomException(){
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CustomException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
