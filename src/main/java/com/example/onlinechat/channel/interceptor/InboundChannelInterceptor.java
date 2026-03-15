package com.example.onlinechat.channel.interceptor;
import com.example.onlinechat.exception.ChannelServiceException;
import com.example.onlinechat.service.ChannelService;
import com.example.onlinechat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.Principal;
import java.util.*;

@Component
@RequiredArgsConstructor
@Log4j2
public class InboundChannelInterceptor implements ChannelInterceptor {
    private final ChatRoomService chatRoomService;
    private final ChannelService channelService;
    private final static List<String> ALLOWED_DEST_PREFIX = List.of(
            "/topic/msg/room/",
            "/topic/notify/room/",
            "/app/msg/room/"
    );

    private String validateUser(StompHeaderAccessor accessor) {
        Principal principal = accessor.getUser();
        if (principal == null) {
            throw new MessageDeliveryException("unknown user");
        }

        return principal.getName();
    }

    private void sendHandler(StompHeaderAccessor accessor) {
        String uid = validateUser(accessor);

        String sessionId = accessor.getSessionId();
        String rid = parseRid(accessor.getDestination());
        String subId = accessor.getFirstNativeHeader("id");

        try {
            channelService.isUserJoinedChannel(subId, rid, sessionId);

        } catch (ChannelServiceException channelServiceException) {
            throw new MessageDeliveryException(channelServiceException.getMessage());
        }
    }

    private String parseRid(String dest) {
        return dest.substring(dest.lastIndexOf('/') + 1);
    }

    private void subscribeHandler(StompHeaderAccessor accessor) {
        String uid = validateUser(accessor);

        String sessionId = accessor.getSessionId();
        String rid = parseRid(accessor.getDestination());
        String subId = accessor.getSubscriptionId();

        if (!chatRoomService.isUserJoinedInChatroom(rid, uid)) {
            throw new MessageDeliveryException("you are not member of this room.");
        }

        try {
            channelService.subscribeChannel(rid, subId, sessionId);

        } catch (ChannelServiceException channelServiceException) {
            throw new MessageDeliveryException(channelServiceException.getMessage());
        }
    }



    private void unsubscribeHandler(StompHeaderAccessor accessor) {
        String uid = validateUser(accessor);

        String sessionId = accessor.getSessionId();
        String subId = accessor.getSubscriptionId();
        String rid = parseRid(accessor.getDestination());

        try {
            if (!channelService.isUserJoinedChannel(subId, rid, sessionId))
                return;

            channelService.unsubscribeChannel(subId, sessionId);

        } catch (ChannelServiceException channelServiceException) {
            throw new MessageDeliveryException(channelServiceException.getMessage());
        }
    }

    private void disconnectHandler(StompHeaderAccessor accessor) {
        String sessionId = accessor.getSessionId();

        channelService.removeSessionId(sessionId);
    }

    public void validateDest(StompHeaderAccessor accessor) {
        String dest = accessor.getDestination();
        if (dest==null || "".startsWith(dest)) {
            throw new MessageDeliveryException("Invalid Destination");
        }

        boolean allowed = ALLOWED_DEST_PREFIX.stream().anyMatch(dest::startsWith);
        if (!allowed) {
            throw new MessageDeliveryException("Invalid destination");
        }
    }

    @Nullable
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();

        if (command == StompCommand.SEND || command == StompCommand.SUBSCRIBE) {
            validateDest(accessor);
        }

        try {
            switch (command) {
                case SUBSCRIBE: {
                    subscribeHandler(accessor);
                    break;
                }

                case SEND: {
                    sendHandler(accessor);
                    break;
                }

                case UNSUBSCRIBE: {
                    unsubscribeHandler(accessor);
                    break;
                }

                case DISCONNECT: {
                    disconnectHandler(accessor);
                }
                default:
                    break;
            }

            return message;

        } catch (MessageDeliveryException messageDeliveryException) {
            log.info(messageDeliveryException.getMessage());
            throw messageDeliveryException;
        } catch (RuntimeException other) {
            log.info("[InboundChannelInterceptor]: " + other.getMessage());
            throw new MessageDeliveryException("Internal Error");
        }
    }



}

