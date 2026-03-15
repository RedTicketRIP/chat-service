package com.example.onlinechat.exception.business;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;


public class ChatroomAlreadyExistException extends BasicException {
    public ChatroomAlreadyExistException(String roomId) {
        this(roomId, null);
    }

    public ChatroomAlreadyExistException(String roomId, Exception e) {
        super(logMessage(roomId),
                userMessage(roomId),
                HttpStatus.NOT_ACCEPTABLE,
                ErrorCode.RESOURCE_ALREADY_EXIST,
                extra());
    }

    private static String[] logMessage(String roomId) {
        String res = "ChatroomAlreadyExist: " + roomId + " already exist";

        return new String[]{ res };
    }

    private static String[] userMessage(String roomId) {
        return new String[]{"chatroom already exist"};
    }

    private static Map<String, Object> extra() {
        return null;
    }
}
