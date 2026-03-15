package com.example.onlinechat.repository.mapper;

import com.example.onlinechat.domain.ChatRoom;
import com.example.onlinechat.domain.User;

import java.util.List;

public interface UserMapper {
    public User findById(String uid);

    public User findUserChatRoomMapping(String rid, String uid);

    public void save(String id, String password, boolean isOnline);

    public List<ChatRoom> findAllChatRoomUserMapping(String uid);

    public void deleteAll();

    public void updateIsOnline(String uid, boolean isOnline);

}
