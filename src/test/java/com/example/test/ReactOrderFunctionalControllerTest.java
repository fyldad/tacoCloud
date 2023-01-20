package com.example.test;

import com.example.test.controller.react.functional.ReactOrderFunctionalController;
import com.example.test.model.Ingredient;
import com.example.test.model.Taco;
import com.example.test.model.TacoOrder;
import com.example.test.repository.reactive.ReactiveOrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReactOrderFunctionalControllerTest {

    @Test
    public void checkReturn() {

        TacoOrder[] orders = {
                testTacoOrder(Arrays.asList(1L, 2L)),
                testTacoOrder(Arrays.asList(3L, 4L)),
                testTacoOrder(Arrays.asList(5L, 6L)),
                testTacoOrder(Arrays.asList(7L, 8L)),
                testTacoOrder(Arrays.asList(9L, 10L)),
                testTacoOrder(Arrays.asList(11L ,12L)),
                testTacoOrder(Arrays.asList(13L ,14L)),
                testTacoOrder(Arrays.asList(15L ,16L))
        };

        ReactiveOrderRepository repository = Mockito.mock(ReactiveOrderRepository.class);
        Flux<TacoOrder> orderFlux = Flux.just(orders);
        when(repository.findAll()).thenReturn(orderFlux);

        WebTestClient testClient = WebTestClient.bindToController(
                new ReactOrderFunctionalController(repository)).build();

        testClient.get().uri("/api/func/orders?recent")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TacoOrder.class)
                .contains(Arrays.copyOf(orders, 5));
    }

    @Test
    public void postTaco() {
        ReactiveOrderRepository repository = Mockito.mock(ReactiveOrderRepository.class);
        WebTestClient testClient = WebTestClient.bindToController(
                new ReactOrderFunctionalController(repository)).build();
        Mono<TacoOrder> unsavedOrderMono = Mono.just(testTacoOrder(Collections.singletonList(1L)));
        TacoOrder savedOrder = testTacoOrder(Collections.singletonList(1L));
        Flux<TacoOrder> savedOrderFlux = Flux.just(savedOrder);
        when(repository.saveAll(any(Mono.class))).thenReturn(savedOrderFlux);

        testClient.post().uri("/api/func/orders")
                .body(unsavedOrderMono, TacoOrder.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TacoOrder.class)
                .isEqualTo(savedOrder);
    }

    private TacoOrder testTacoOrder(List<Long> numbers) {
        List<Taco> tacos = numbers.stream().map(this::testTaco).toList();
        TacoOrder order = new TacoOrder();
        order.setId(numbers.get(0).toString());
        order.setPlacedAt(new Date());
        order.setTacos(tacos);
        return order;
    }

    private Taco testTaco(Long number) {
        Taco taco = new Taco();
        taco.setName("Taco " + number);
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(
                new Ingredient("INGA", "Ingredient A", Ingredient.Type.WRAP));
        ingredients.add(
                new Ingredient("INGB", "Ingredient B", Ingredient.Type.PROTEIN));
        taco.setIngredients(ingredients);
        return taco;
    }

}