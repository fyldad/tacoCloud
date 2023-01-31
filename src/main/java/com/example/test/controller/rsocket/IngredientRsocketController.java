package com.example.test.controller.rsocket;

import com.example.test.model.Ingredient;
import com.example.test.repository.reactive.ReactiveIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IngredientRsocketController {

    private final ReactiveIngredientRepository repository;

    @MessageMapping("ingredient/{type}")
    public Flux<Ingredient> getIngredientsByType(@DestinationVariable("type") Ingredient.Type type) {
        return repository.findAllByType(type).delayElements(Duration.ofSeconds(1));
    }

    @MessageMapping("ingredient")
    public Mono<Void> saveIngredient(Mono<Ingredient> ingredientMono) {
        return ingredientMono.doOnNext(ingredient -> {
            log.info("received new ingredient : {}", ingredient);
        }).thenEmpty(Mono.empty());
    }

    @MessageMapping("ingredientFlux")
    public Flux<Ingredient> getIngredientId(Flux<String> ingredientIdFlux) {
        return ingredientIdFlux
                .doOnNext(in -> log.info("calculating ingredient for id: {}", in))
                .flatMap(repository::findById);
    }

}
