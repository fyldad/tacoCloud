package com.example.test.controller.react.functional;

import com.example.test.model.TacoOrder;
import com.example.test.repository.reactive.ReactiveOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Objects;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class ReactOrderFunctionalController {

    private final ReactiveOrderRepository repository;

    @Bean
    public RouterFunction<?> routerFunction() {
        return route(GET("/api/func/orders").and(queryParam("recent", Objects::nonNull)), this::recent)
                .andRoute(POST("/api/func/orders"), this::postOrder);
    }

    private Mono<ServerResponse> postOrder(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TacoOrder.class)
                .flatMap(repository::save)
                .flatMap(savedOrder ->
                        ServerResponse.created(URI.create(
                                        "https://localhost:8443/api/func/orders" + savedOrder.getId()))
                                .body(savedOrder, TacoOrder.class));
    }

    private Mono<ServerResponse> recent(ServerRequest serverRequest) {
        return ServerResponse.ok().body(repository.findAll().take(5), TacoOrder.class);
    }

}
