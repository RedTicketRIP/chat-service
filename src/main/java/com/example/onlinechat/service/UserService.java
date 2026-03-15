package com.example.onlinechat.service;

import com.example.onlinechat.domain.ChatRoom;
import com.example.onlinechat.domain.User;
import com.example.onlinechat.dto.ChatRoomDTO;
import com.example.onlinechat.dto.UserDTO;
import com.example.onlinechat.dto.UserSummaryDTO;
import com.example.onlinechat.exception.business.UserAlreadyExistException;
import com.example.onlinechat.exception.business.UserNotFoundException;
import com.example.onlinechat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findById(String uid) {
        return modelMapper.map(userRepository.findById(uid).orElseThrow(
                () -> new UserNotFoundException(uid)
        ), UserDTO.class);
    }

    public UserSummaryDTO findSummaryById(String uid) {
        return modelMapper.map(userRepository.findById(uid).orElseThrow(
                () -> new UserNotFoundException(uid)
        ), UserSummaryDTO.class);
    }

    public void updateIsOnline(String uid, boolean isOnline) {
        userRepository.updateIsOnline(uid, isOnline);
    }

    public void addUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsOnline(false);

        try {
            userRepository.save(user);

        } catch (DuplicateKeyException duplicateKeyException) {
            throw new UserAlreadyExistException(user.getId(), duplicateKeyException);
        }
    }

    public List<ChatRoomDTO> getUserParticipating(String uid) {
        List<ChatRoom> chatRoomList = userRepository.findAllChatRoomUserMapping(uid);

        return chatRoomList.stream().map(chatRoom -> {
            return modelMapper.map(chatRoom, ChatRoomDTO.class);
        }).toList();
    }


    public void logout(String uid) {
        userRepository.updateIsOnline(uid, false);
    }
}
