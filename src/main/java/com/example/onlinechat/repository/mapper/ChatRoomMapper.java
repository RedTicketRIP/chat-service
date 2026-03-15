package com.example.onlinechat.repository.mapper;

import com.example.onlinechat.domain.ChatRoom;
import com.example.onlinechat.domain.User;

import java.util.List;

public interface ChatRoomMapper {
    public ChatRoom findById(String rid);


    public ChatRoom findByIdForUpdate(String rid);

    public List<ChatRoom> findAll();

    public void save(String rid, String ownerId);

    public void deleteById(String rid);

    public void addUserChatRoomMapping(String rid, String uid);

    public int removeUserChatRoomMapping(String rid, String uid);

    public List<User> findAllUserChatRoomMapping(String rid);

    public void deleteAll();

    public Boolean isExistUserChatroomMatch(String rid, String uid);
}
