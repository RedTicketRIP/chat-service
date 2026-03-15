package com.example.onlinechat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Chat {
    private String user_id;
    private String room_id;

    private String content;

    private LocalDateTime sendTime;

    private boolean isNotify;
}
