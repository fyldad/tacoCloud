package com.example.test.messaging.jmx;

import com.example.test.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@ManagedResource
@RequiredArgsConstructor
public class MBeanComponent {

    private final OrderRepository repository;
    private final AtomicLong counter = new AtomicLong();

    @ManagedAttribute
    public long getOrderCount() {
        return counter.get() + repository.count();
    }

    @ManagedOperation
    public long increment(long delta) {
        return counter.addAndGet(delta);
    }

}
