package com.example.test.messaging.jms;

import com.example.test.model.TacoOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JmsOrderPullReceiver {

    private final JmsTemplate jms;

    public TacoOrder receiveOrder() {
        return (TacoOrder) jms.receiveAndConvert();
    }

}
