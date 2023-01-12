package com.example.test.controller.rest;

import com.example.test.messaging.jms.JmsOrderPullReceiver;
import com.example.test.messaging.rabbit.RabbitOrderPullReceiver;
import com.example.test.model.TacoOrder;
import com.example.test.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8443")
public class RestOrderController {

    private final OrderRepository orderRepository;
    private final JmsOrderPullReceiver jmsOrderPullReceiver;
    private final RabbitOrderPullReceiver rabbitOrderPullReceiver;

    @GetMapping(params = "recent")
    public List<TacoOrder> recentOrders() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("placedAt").descending());
        return orderRepository.findAll(pageRequest);
    }

    @GetMapping("jms")
    public TacoOrder getOrderFromJms() {
        return jmsOrderPullReceiver.receiveOrder();
    }

    @GetMapping("rabbit")
    public TacoOrder getOrderFromRabbit() {
        return rabbitOrderPullReceiver.receiveOrder();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TacoOrder> tacoById(@PathVariable String id) {
        return orderRepository.findById(id)
                .map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TacoOrder postOrder(@RequestBody TacoOrder order) {
        return orderRepository.save(order);
    }

}
