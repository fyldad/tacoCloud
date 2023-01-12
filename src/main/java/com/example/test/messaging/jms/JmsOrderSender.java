package com.example.test.messaging.jms;

import com.example.test.messaging.OrderSender;
import com.example.test.model.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsOrderSender implements OrderSender {

    private final JmsTemplate jms;

    @Override
    public void send(TacoOrder order) {
        jms.convertAndSend(order, message -> {
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }

}
