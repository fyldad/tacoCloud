package com.example.test.messaging.rabbit;

import com.example.test.model.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitOrderPullReceiver {

    private final RabbitTemplate rabbitTemplate;

    public TacoOrder receiveOrder() {
        return rabbitTemplate.receiveAndConvert(new ParameterizedTypeReference<>() {
        });
    }

}
