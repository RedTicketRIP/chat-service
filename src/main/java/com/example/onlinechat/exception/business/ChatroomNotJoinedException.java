package com.example.onlinechat.exception.business;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;


public class ChatroomNotJoinedException extends BasicException {
    public ChatroomNotJoinedException(String rid, String uid) {
        this(rid, uid, null);
    }

    public ChatroomNotJoinedException(String rid, String uid, Exception e) {
        super(logMessage(rid, uid),
                userMessage(rid, uid),
                HttpStatus.NOT_ACCEPTABLE,
                ErrorCode.FORBIDDEN_ACTION,
                extra());
    }

    private static String[] logMessage(String rid, String uid) {
        String res = "ChatroomNotJoined: uid="+uid+", rid="+rid +
                "attempted to leave without join";

        return new String[]{ res };
    }

    private static String[] userMessage(String rid, String uid) {
        return new String[]{"you are not member of this room"};
    }

    private static Map<String, Object> extra() {
        return null;
    }
}
