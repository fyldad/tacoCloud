package com.example.test.config.messaging;

import com.example.test.model.TacoOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

@Slf4j
public class KafkaOrderDeserializer implements Deserializer<TacoOrder> {
    @Override
    public TacoOrder deserialize(String s, byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new String(bytes, StandardCharsets.UTF_8), TacoOrder.class);
        } catch (JsonProcessingException e) {
            log.error("Unable to deserialize message" , e);
            return null;
        }
    }
}
