package com.example.onlinechat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatDTO {
    @NotBlank(message = "")
    private String user_id;

    @NotBlank(message = "")
    private String room_id;

    @NotBlank(message = "")
    private String content;

    private boolean isNotify;

    private LocalDateTime sendTime;
}
