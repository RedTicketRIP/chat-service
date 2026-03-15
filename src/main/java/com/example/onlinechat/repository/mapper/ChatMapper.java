package com.example.onlinechat.repository.mapper;

import com.example.onlinechat.domain.Chat;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMapper {
    public Chat findById(Long id);

    public List<Chat> findChatHistory(String rid, String uid);

    public void save(String uid, String rid, String content, LocalDateTime sendTime, boolean isNotify);

    public void deleteAll();

}
