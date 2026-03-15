package com.example.onlinechat.dto;

import com.example.onlinechat.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorLog {
    private ErrorCode errCode;
    private String[] userMessage;
    private String[] logMessage;
    private HttpStatus status;
    private Throwable exception;
    private Map<String, Object> extra;
}
