package com.silkrode.ai.notification.interceptor;

import com.silkrode.ai.notification.service.AuthGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
@Slf4j
@AllArgsConstructor
public class AuthChannelInterceptor implements ChannelInterceptor {

    private final AuthGroupService authGroupService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor == null || accessor.getCommand() == null) return null;
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String login = accessor.getLogin();
            String passcode = accessor.getPasscode();
            if (StringUtils.isEmpty(login)
                    || StringUtils.isEmpty(passcode)
                    || !authGroupService.authenticate(login, passcode)
            ) return null;
        }
        return message;
    }
}
