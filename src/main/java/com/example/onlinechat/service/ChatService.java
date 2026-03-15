package com.example.onlinechat.service;

import com.example.onlinechat.domain.Chat;
import com.example.onlinechat.dto.ChatDTO;
import com.example.onlinechat.exception.business.ChatroomNotFoundException;
import com.example.onlinechat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ModelMapper modelMapper;

    public List<ChatDTO> findChatHistory(String rid, String uid) {
        return chatRepository.findChatHistory(rid, uid).stream().map(chat -> {
            return modelMapper.map(chat, ChatDTO.class);
        }).toList();
    }


    public void storeChat(ChatDTO chatDTO) {
        Chat chat = modelMapper.map(chatDTO, Chat.class);

        try {
            chatRepository.save(chat);

        } catch (DataIntegrityViolationException integrityViolationException) {
            throw new ChatroomNotFoundException(chatDTO.getRoom_id());
        }
    }
}
