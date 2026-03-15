package com.example.onlinechat.exception.business;

import com.example.onlinechat.exception.BasicException;
import com.example.onlinechat.exception.ErrorCode;
import org.springframework.http.HttpStatus;

import java.util.Map;


public class NonOwnerRoomDeleteException extends BasicException {
    public NonOwnerRoomDeleteException(String rid, String uid) {
        this(rid, uid, null);
    }

    public NonOwnerRoomDeleteException(String rid, String uid, Exception e) {
        super(logMessage(rid, uid),
                userMessage(rid, uid),
                HttpStatus.NOT_ACCEPTABLE,
                ErrorCode.FORBIDDEN_ACTION,
                extra());
    }

    private static String[] logMessage(String rid, String uid) {
        StringBuilder builder = new StringBuilder();

        builder.append("NonOwnerRoomDelete: rid="+ rid)
                .append(", uid="+uid)
                .append(": tried to delete room.");

        return new String[]{ builder.toString()};
    }

    private static String[] userMessage(String rid, String uid) {
        return new String[]{"Only the owner can delete the room"};
    }

    private static Map<String, Object> extra() {
        return null;
    }
}
