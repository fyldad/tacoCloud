package com.example.test.controller.react;

import com.example.test.model.TacoOrder;
import com.example.test.repository.reactive.ReactiveOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/reactOrders", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:8443")
public class ReactOrderController {

    private final ReactiveOrderRepository repository;

    @GetMapping
    public Flux<TacoOrder> recentOrders() {
        return repository.findAll().take(5);
    }

    @GetMapping("/{id}")
    public Mono<TacoOrder> orderById(@PathVariable String id) {
        return repository.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TacoOrder> postOrder(@RequestBody Mono<TacoOrder> orderMono) {
        return repository.saveAll(orderMono).next();
    }

}
