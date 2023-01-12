package com.example.test.messaging.kafka;

import com.example.test.messaging.OrderSender;
import com.example.test.model.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaOrderSender implements OrderSender {

    private final KafkaTemplate<String, TacoOrder> template;

    @Override
    public void send(TacoOrder order) {
        template.sendDefault(order);
    }
}
