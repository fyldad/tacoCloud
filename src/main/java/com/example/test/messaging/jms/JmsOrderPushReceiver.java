package com.example.test.messaging.jms;

import com.example.test.model.TacoOrder;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JmsOrderPushReceiver {

    @JmsListener(destination = "${queue.name}")
    public void receiveOrder(TacoOrder order) {
        log.info("JMS: received new order: {}", order);
    }

}
