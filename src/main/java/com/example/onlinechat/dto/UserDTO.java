package com.example.onlinechat.dto;

import com.example.onlinechat.dto.validation.anno.NoWhiteSpace;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @NoWhiteSpace(message = "id 는 공백이 포함되어있을 수 없습니다.")
    @NotBlank(message="id 는 비어있을 수 없습니다.")
    private String id;

    @NotBlank(message="password 는 비어있을 수 없습니다.")
    private String password;

    @Setter
    private LocalDateTime registerDate;

    public void setId(String id) {
        this.id = id.trim();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }

}
