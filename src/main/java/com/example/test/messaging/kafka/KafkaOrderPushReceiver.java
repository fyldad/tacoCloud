package com.example.test.messaging.kafka;

import com.example.test.model.TacoOrder;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class KafkaOrderPushReceiver {

    @KafkaListener(topics = "${queue.name}")
    public void receiveOrder(TacoOrder order) {
        log.info("Kafka: received new order: {}", order);
    }

}
