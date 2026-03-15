package com.example.onlinechat.exception.business;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;


public class OwnerLeaveException extends BasicException {
    public OwnerLeaveException(String rid, String uid) {
        this(rid, uid, null);
    }

    public OwnerLeaveException(String rid, String uid, Exception e) {
        super(logMessage(rid, uid),
                userMessage(rid, uid),
                HttpStatus.NOT_ACCEPTABLE,
                ErrorCode.FORBIDDEN_ACTION,
                extra());
    }

    private static String[] logMessage(String rid, String uid) {
        String res = "OwnerLeave: owner of the " + rid + " " + uid + " tried to leave.";

        return new String[]{ res };
    }

    private static String[] userMessage(String rid, String uid) {
        return new String[]{"chatroom already exist"};
    }

    private static Map<String, Object> extra() {
        return null;
    }
}
