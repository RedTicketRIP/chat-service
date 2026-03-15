package com.example.onlinechat.exception.business;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;


public class ChatroomAlreadyJoinedException extends BasicException {
    public ChatroomAlreadyJoinedException(String rid, String uid) {
        this(rid, uid, null);
    }

    public ChatroomAlreadyJoinedException(String rid, String uid, Exception e) {
        super(logMessage(rid, uid),
                userMessage(rid, uid),
                HttpStatus.NOT_ACCEPTABLE,
                ErrorCode.RESOURCE_ALREADY_EXIST,
                extra());
    }

    private static String[] logMessage(String rid, String uid) {
        String res = "ChatroomAlreadyJoined: " + uid + " is already joined in " + rid;

        return new String[]{ res };
    }

    private static String[] userMessage(String rid, String uid) {
        return new String[]{"user already joined"};
    }

    private static Map<String, Object> extra() {
        return null;
    }
}
