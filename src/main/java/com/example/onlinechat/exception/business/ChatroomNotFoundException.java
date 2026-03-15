package com.example.onlinechat.exception.business;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;


public class ChatroomNotFoundException extends BasicException {
    public ChatroomNotFoundException(String roomId) {
        this(roomId, null);
    }

    public ChatroomNotFoundException(String roomId, Exception e) {
        super(logMessage(roomId),
                userMessage(roomId),
                HttpStatus.NOT_FOUND,
                ErrorCode.RESOURCE_NOT_FOUND,
                extra());
    }

    private static String[] logMessage(String roomId) {
        String res = "ChatroomNotFound: rid="+ roomId + " not found.";

        return new String[]{ res };
    }

    private static String[] userMessage(String roomId) {
        return new String[]{"chatroom not found."};
    }

    private static Map<String, Object> extra() {
        return null;
    }
}
