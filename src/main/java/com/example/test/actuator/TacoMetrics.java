package com.example.test.actuator;

import com.example.test.model.TacoOrder;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
public class TacoMetrics extends AbstractRepositoryEventListener<TacoOrder> {
    // TODO: 01.02.2023 does not work. not invoked
    @Override
    protected void onAfterCreate(TacoOrder order) {
        System.out.println("--------------AFTER CREATE EVENT");
    }
}
