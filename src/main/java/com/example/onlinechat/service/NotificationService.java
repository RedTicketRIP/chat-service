package com.example.onlinechat.service;

import com.example.onlinechat.domain.Chat;
import com.example.onlinechat.dto.ChatDTO;
import com.example.onlinechat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatRepository chatRepository;
    private static final String notifyPath = "/topic/notify/room/";

    public void notify(String rid, ChatDTO chatDTO) {
        messagingTemplate.convertAndSend(notifyPath + rid, chatDTO);
    }


    public void notifyLeaveUser(String rid, String username) {
        Chat chat = Chat.builder()
                .content(username + " leaved")
                .room_id(rid)
                .isNotify(true)
                .sendTime(LocalDateTime.now())
                .build();

        chatRepository.save(chat);
        messagingTemplate.convertAndSend(notifyPath + rid, chat);
    }

    public void notifyJoinUser(String rid, String username) {
        Chat chat = Chat.builder()
                .content(username + " joined")
                .room_id(rid)
                .isNotify(true)
                .sendTime(LocalDateTime.now())
                .build();

        chatRepository.save(chat);
        messagingTemplate.convertAndSend(notifyPath + rid, chat);
    }

    public void notifyCreateRoom(String rid) {
        Chat chat = Chat.builder()
                .content("room created")
                .room_id(rid)
                .isNotify(true)
                .sendTime(LocalDateTime.now())
                .build();

        chatRepository.save(chat);
        messagingTemplate.convertAndSend(notifyPath + rid, chat);
    }

    public void notifyDeleteRoom(String rid) {
        Chat chat = Chat.builder()
                .content("room deleted")
                .room_id(rid)
                .isNotify(true)
                .sendTime(LocalDateTime.now())
                .build();

        chatRepository.save(chat);
        messagingTemplate.convertAndSend(notifyPath + rid, chat);
    }

}
