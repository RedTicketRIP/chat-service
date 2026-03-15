package com.example.onlinechat.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


// 다른 사용자에게 특정 사용자정보 전달해줄때 사용.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSummaryDTO {
    private String id;
    private LocalDateTime registerDate;
}
