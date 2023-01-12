package com.example.test.config;

import com.example.test.model.TacoOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class KafkaOrderSerializer implements Serializer<TacoOrder> {
    @Override
    public byte[] serialize(String s, TacoOrder order) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(order);
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize object {}", order, e);
            return null;
        }
    }
}
