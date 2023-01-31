package com.example.test.config.actuator;

import com.example.test.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderInfoContributor implements InfoContributor {

    private final OrderRepository orderRepository;

    @Override
    public void contribute(Info.Builder builder) {
        long count = orderRepository.count();
        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("count", count);
        builder.withDetail("order-status", orderMap);
    }
}
