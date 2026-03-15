package com.example.onlinechat.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public abstract class BasicException extends RuntimeException{
    private String[] logMessage;
    private String[] userMessage;
    private ErrorCode errCode;
    private HttpStatus status;
    private Map<String, Object> extra;

    public BasicException(String[] logMessage, String[] userMessage, HttpStatus status, ErrorCode errCode) {
        this(logMessage, userMessage, status, errCode, null, null);
    }

    public BasicException(String[] logMessage, String[] userMessage, HttpStatus status, ErrorCode errCode, Map<String, Object> extra) {
        this(logMessage, userMessage, status, errCode, null, extra);
    }

    public BasicException(String[] logMessage, String[] userMessage, HttpStatus status, ErrorCode errCode, Exception e) {
        this(logMessage, userMessage, status, errCode, e, null);
    }

    public BasicException(String[] logMessage, String[] userMessage, HttpStatus status, ErrorCode errCode, Exception e, Map<String, Object> extra) {
        super(String.join(",", logMessage), e);
        this.logMessage = logMessage;
        this.userMessage = userMessage;
        this.status = status;
        this.extra = extra;
        this.errCode = errCode;
    }


}
