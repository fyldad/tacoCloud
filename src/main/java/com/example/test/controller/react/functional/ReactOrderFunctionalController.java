package com.example.test.controller.react.functional;

import com.example.test.model.TacoOrder;
import com.example.test.repository.reactive.ReactiveOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class ReactOrderFunctionalController {

    private final ReactiveOrderRepository repository;

    @Autowired
    public ReactOrderFunctionalController(ReactiveOrderRepository repository) {
        this.repository = repository;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
// TODO: 01.02.2023 does not work 404 not found
        return route(GET("/api/func/orders"), request -> ok().body(repository.findAll(), TacoOrder.class));

//        return route(GET("/api/func/orders"), this::recent)
//                .and(route(POST("/api/func/orders"), this::postOrder));
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
        return ok().body(repository.findAll().take(5), TacoOrder.class);
    }

}
