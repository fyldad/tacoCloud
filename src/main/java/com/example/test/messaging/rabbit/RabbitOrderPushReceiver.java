package com.example.test.messaging.rabbit;

import com.example.test.model.TacoOrder;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class RabbitOrderPushReceiver {

    @RabbitListener(queues = "${queue.name}")
    public void receiveOrder(TacoOrder order) {
        log.info("RabbitMQ: received new order: {}", order);
    }

}
