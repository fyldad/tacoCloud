package com.example.test.messaging.rabbit;

import com.example.test.messaging.OrderSender;
import com.example.test.model.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitOrderSender implements OrderSender {

    private final RabbitTemplate template;

    @Override
    public void send(TacoOrder order) {
        template.convertAndSend(order, message -> {
            message.getMessageProperties().setHeader("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }

}
