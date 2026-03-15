package com.example.onlinechat.controller.public_api;

import com.example.onlinechat.dto.ChatRoomDTO;
import com.example.onlinechat.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @Operation(summary = "존재하는 모든 채팅방 반환.")
    @GetMapping("/all")
    public List<ChatRoomDTO> findAllChatRoom() {
        return chatRoomService.findAllChatRoom();
    }

}
