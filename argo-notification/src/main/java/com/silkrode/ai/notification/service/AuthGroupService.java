package com.silkrode.ai.notification.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
@RefreshScope
public class AuthGroupService {

    @Value("#{${organization.groups}}")
    private Map<String, String> group;

    public Boolean authenticate(String login, String passcode) {
        String token = group.get(login);
        return passcode.equals(token);
    }
}
