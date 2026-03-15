package com.example.onlinechat.repository;


import com.example.onlinechat.domain.Chat;
import com.example.onlinechat.repository.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRepository {
    private final ChatMapper chatMapper;

    // 특정 채팅룸에 속한 특정 유저의 채팅목록 불러오기.
    public List<Chat> findChatHistory(String rid, String uid) {
        return chatMapper.findChatHistory(rid, uid);
    }

    public void save(Chat chat) {
        String user_id = chat.getUser_id();
        String room_id = chat.getRoom_id();
        String content = chat.getContent();
        boolean isNotify = chat.isNotify();
        LocalDateTime sendTime = chat.getSendTime();

        chatMapper.save(user_id, room_id, content, sendTime, isNotify);
    }

    public void deleteAll() {
        chatMapper.deleteAll();
    }
}
