package com.silkrode.ai.stream.service.impl;

import com.silkrode.ai.common.dto.DataDto;
import com.silkrode.ai.stream.repository.impl.StreamRepositoryImpl;
import com.silkrode.ai.stream.service.StreamService;
import org.springframework.stereotype.Service;

/**
 * @implNote  : Implement Stream service.
 * @param : dataDto from Stream controller.
 */
@Service
public class StreamServiceImpl implements StreamService {

    private final StreamRepositoryImpl streamRepository;

    public StreamServiceImpl(StreamRepositoryImpl streamRepository) {
        this.streamRepository = streamRepository;
    }

    @Override
    public void sendMessage(DataDto dataDto) {
        streamRepository.sendMessage(dataDto.getData());
    }
}
