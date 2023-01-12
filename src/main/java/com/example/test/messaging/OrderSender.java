package com.example.test.messaging;

import com.example.test.model.TacoOrder;

public interface OrderSender {
    public void send(TacoOrder order);
}
