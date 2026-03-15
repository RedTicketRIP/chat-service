package com.example.onlinechat.exception;

import lombok.Data;

@Data
public class ChannelServiceException extends RuntimeException {
    private String message;

    public ChannelServiceException(String message) {
        super(message);
        this.message = message;
    }

}
