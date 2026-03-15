package com.example.onlinechat.controller;

import com.example.onlinechat.dto.ChatDTO;
import com.example.onlinechat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/msg/room/{rid}")
    public void sendChat(
            @DestinationVariable("rid") String rid,
            @Payload Map<String, String> json,
            Message<?> message)
    {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        ChatDTO chatDTO = ChatDTO.builder()
                .user_id(accessor.getUser().getName())
                .room_id(rid)
                .content(json.get("content"))
                .sendTime(LocalDateTime.now())
                .isNotify(false)
                .build();

        chatService.storeChat(chatDTO);

        messagingTemplate.convertAndSend("/topic/msg/room/" + rid, chatDTO);
    }
}
