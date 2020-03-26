package com.silkrode.ai.stream.service;

import com.silkrode.ai.common.dto.DataDto;
/**
 * @implSpec  : define Stream service
 */
public interface StreamService {
    void sendMessage(DataDto dataDto);
}
