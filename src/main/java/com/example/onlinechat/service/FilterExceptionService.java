package com.example.onlinechat.service;

import com.example.onlinechat.dto.ErrorLog;
import com.example.onlinechat.dto.ErrorResponse;
import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FilterExceptionService {

    public void responseError(BasicException e, HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errCode(e.getErrCode())
                .detail(e.getUserMessage())
                .build();
        ErrorLog errorLog = ErrorLog.builder()
                .errCode(e.getErrCode())
                .userMessage(e.getLogMessage())
                .logMessage(e.getLogMessage())
                .status(e.getStatus())
                .exception(e.getCause())
                .extra(e.getExtra())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(e.getStatus().value());
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
