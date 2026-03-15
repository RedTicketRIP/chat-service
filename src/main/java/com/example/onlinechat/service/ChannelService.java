package com.example.onlinechat.service;

import com.example.onlinechat.exception.ChannelServiceException;
import com.example.onlinechat.repository.interf.ChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChannelService {
    private final ChannelRepository channelRepository;

    public void subscribeChannel(String dest, String subId, String sessionId) {
        try {
            channelRepository.addUserInChannel(dest, subId, sessionId);

        } catch (IllegalStateException illegalStateException) {
            throw new ChannelServiceException(illegalStateException.getMessage());
        } catch (RuntimeException other) {
            throw new ChannelServiceException("Internal Error");
        }
    }

    public void unsubscribeChannel(String subId, String sessionId) {
        try {
            channelRepository.removeUserInChannel(subId, sessionId);

        } catch (IllegalStateException illegalStateException) {
            throw new ChannelServiceException(illegalStateException.getMessage());
        } catch (RuntimeException other) {
            log.info(other.getMessage());
            throw new ChannelServiceException("Internal Error");
        }

    }

    public boolean isUserJoinedChannel(String subId, String rid, String sessionId) {
        try {
            return channelRepository.isExistUserChannelMatch(subId, rid, sessionId);

        } catch (IllegalStateException illegalStateException) {
            throw new ChannelServiceException(illegalStateException.getMessage());
        } catch (RuntimeException other) {
            throw new ChannelServiceException("Internal Error");
        }
    }

    public void removeSessionId(String sessionId) {
        channelRepository.removeSessionId(sessionId);
    }
}
