package com.silkrode.ai.stream.repository;

/**
 * @implSpec  : The repository of kafka stream.
 */
public interface StreamRepository {

    void sendMessage(Object data);

}
