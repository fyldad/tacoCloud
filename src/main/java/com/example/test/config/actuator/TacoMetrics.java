package com.example.test.config.actuator;

import com.example.test.model.TacoOrder;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
public class TacoMetrics extends AbstractRepositoryEventListener<TacoOrder> {

    @Override
    protected void onAfterCreate(TacoOrder order) {
        System.out.println("--------------AFTER CREATE EVENT");
    }
}
