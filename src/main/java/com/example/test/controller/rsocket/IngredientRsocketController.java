package com.example.test.controller.rsocket;

import com.example.test.model.Ingredient;
import com.example.test.repository.reactive.ReactiveIngredientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IngredientRsocketController {

    private final ReactiveIngredientRepository repository;

    @MessageMapping("ingredient/{type}")
    public Flux<Ingredient> getIngredientsByType(@DestinationVariable("type") Ingredient.Type type) {
        return repository.findAllByType(type);
    }

}
