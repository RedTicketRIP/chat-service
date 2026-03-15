package com.example.onlinechat.dto;

import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    @Null(message = "지정하지마라.")
    private String id;

    private String ownerId;

    private LocalDateTime createdTime;

    private List<UserSummaryDTO> joinerList = new ArrayList<>();

    public void addJoinerList(UserSummaryDTO userSummaryDTO) {
        joinerList.add(userSummaryDTO);
    }

}
