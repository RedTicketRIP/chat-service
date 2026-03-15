package com.example.onlinechat.exception.advice;

import com.example.onlinechat.dto.ErrorLog;
import com.example.onlinechat.dto.ErrorResponse;
import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {
    @ExceptionHandler(value = AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> authorizationDeniedException(AuthorizationDeniedException e) {
        ErrorResponse response = ErrorResponse.builder()
                .errCode(ErrorCode.ACCESS_DENIED)
                .detail(new String[]{"Login First"})
                .build();

        ErrorLog errorLog = ErrorLog.builder()
                .errCode(ErrorCode.ACCESS_DENIED)
                .userMessage(new String[]{"Login First"})
                .logMessage(new String[]{"AuthorizationDenied: occurred"})
                .exception(e)
                .extra(null)
                .build();

        return ResponseEntity.status(403).body(response);
    }


    @ExceptionHandler(value= BasicException.class)
    public ResponseEntity<ErrorResponse> basicExceptionHandler(BasicException e) {
        String[] userMessage = e.getUserMessage();
        String[] logMessage = e.getLogMessage();
        ErrorCode errCode = e.getErrCode();
        HttpStatus status = e.getStatus();

        for (String message : logMessage) {
            log.error(message);
        }

        ErrorResponse response = ErrorResponse.builder()
                .errCode(errCode)
                .detail(userMessage)
                .build();

        ErrorLog errLog = ErrorLog.builder()
                .errCode(errCode)
                .userMessage(userMessage)
                .logMessage(logMessage)
                .exception(e.getCause())
                .extra(e.getExtra())
                .build();

        return ResponseEntity.status(status).body(response);
    }

    // 모든 예외는 service 에서 의미있는 예외로 바뀌어서 advice 로 온다.
    // BasicException 을 상속하지 않은 상태로 올라오는 예외는 예상하지 못한 예외다.
    // 로그남기고 처리하자.
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> unexpectedException(RuntimeException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "wait.");

        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }


    @MessageExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> businessExceptionHandler1(RuntimeException e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", e.getMessage());

        log.info(e.getMessage());
        return ResponseEntity.status(500).body(map);
    }
}
